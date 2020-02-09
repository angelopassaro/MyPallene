package com.passaro.mypallene.syntax;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class Variable extends Expr implements Leaf<String> {

    private String name;

    /**
     * @param leftLocation  The left location
     * @param rightLocation The right location
     * @param name          The id
     */

    public Variable(Location leftLocation, Location rightLocation, String name) {
        super(leftLocation, rightLocation);
        this.name = name;
    }

    /**
     * @return The name
     */
    @Override
    public String getName() {
        return name;
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}