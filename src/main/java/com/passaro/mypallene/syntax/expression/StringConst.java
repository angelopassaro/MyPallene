package com.passaro.mypallene.syntax.expression;

import com.passaro.mypallene.syntax.Leaf;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class StringConst extends Expr implements Leaf<String> {

    private final String value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public StringConst(Location leftLocation, Location rightLocation, String value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }

    /**
     * @return The value
     */
    @Override
    public String getName() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
