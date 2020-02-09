package com.passaro.mypallene.syntax;

import com.passaro.mypallene.syntax.typedenoter.TypeDenoter;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class ParDecl extends AstNode {

    private Variable variable;
    private TypeDenoter type;

    /**
     * {@inheritDoc}
     *
     * @param variable The variable
     * @param type     The param com.passaro.mypallene.syntax.type
     */
    public ParDecl(Location leftLocation, Location rightLocation, Variable variable, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.variable = variable;
        this.type = type;
    }


    /**
     * @return The id
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @return The com.passaro.mypallene.syntax.type
     */
    public TypeDenoter getTypeDenoter() {
        return type;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
