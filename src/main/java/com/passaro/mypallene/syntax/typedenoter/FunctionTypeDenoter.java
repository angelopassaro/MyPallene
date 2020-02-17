package com.passaro.mypallene.syntax.typedenoter;

import com.passaro.mypallene.nodetype.CompositeNodeType;
import com.passaro.mypallene.nodetype.FunctionNodeType;
import com.passaro.mypallene.nodetype.NodeType;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringJoiner;


public class FunctionTypeDenoter extends TypeDenoter {

    private LinkedList<TypeDenoter> typeDenoters;
    private TypeDenoter returnTypeDenoter;

    public FunctionTypeDenoter(Location leftLocation, Location rightLocation, LinkedList<TypeDenoter> typeDenoters, TypeDenoter returnTypeDenoter) {
        super(leftLocation, rightLocation);
        this.returnTypeDenoter = returnTypeDenoter;
        this.typeDenoters = typeDenoters;
    }

    public FunctionTypeDenoter(Location leftLocation, Location rightLocation, TypeDenoter returnTypeDenoter) {
        super(leftLocation, rightLocation);
        this.returnTypeDenoter = returnTypeDenoter;
        this.typeDenoters = new LinkedList<>();
    }

    /**
     * @return the returnType
     */
    public TypeDenoter getReturnType() {
        return returnTypeDenoter;
    }

    /**
     * @return the types
     */
    public LinkedList<TypeDenoter> getTypeDenoters() {
        return typeDenoters;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }


    public CompositeNodeType domain() {
        CompositeNodeType compositeNodeType = new CompositeNodeType(new ArrayList<>());
        this.getTypeDenoters().forEach(typeDenoter -> compositeNodeType.addType(typeDenoter.typeFactory()));
        return compositeNodeType;
    }

    public NodeType codomain() {
        return this.getReturnType().typeFactory();
    }

    public String cType() {
        StringJoiner joiner = new StringJoiner(", ");
        this.getTypeDenoters().stream().map(TypeDenoter::cType).forEach(joiner::add);
        return String.format("%s (*%s) (%s)", this.getReturnType().typeFactory(), "%s", joiner.toString());
    }

    @Override
    public NodeType typeFactory() {
        return new FunctionNodeType(domain(), codomain());
    }
}
