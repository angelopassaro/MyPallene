package com.passaro.mypallene.syntax.typedenoter;

import com.passaro.mypallene.nodetype.ArrayNodeType;
import com.passaro.mypallene.nodetype.NodeType;
import com.passaro.mypallene.nodetype.PrimitiveNodeType;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;


public class ArrayTypeDenoter extends TypeDenoter {

    private TypeDenoter type;

    /**
     * {@inheritDoc}
     *
     * @param type The com.passaro.mypallene.syntax.type
     */
    public ArrayTypeDenoter(Location leftLocation, Location rightLocation, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.type = type;
    }

    /**
     * @return The com.passaro.mypallene.syntax.type
     */
    public TypeDenoter getTypeDenoter() {
        return type;
    }

    /**
     * @return type of element
     */
    public NodeType getElementsType() {
        return this.type.typeFactory();
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public NodeType typeFactory() {
        return new ArrayNodeType((PrimitiveNodeType) this.type.typeFactory());
    }

    public String cType() {
        switch ((PrimitiveNodeType) this.getElementsType()) {
            case STRING:
                return "ArrayChar";
            case INT:
                return "ArrayInt";
            case FLOAT:
                return "ArrayFloat";
            case BOOL:
                return "ArrayBool";
            default:
                return null;
        }
    }
}
