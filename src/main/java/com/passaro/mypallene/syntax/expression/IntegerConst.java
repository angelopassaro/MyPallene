package com.passaro.mypallene.syntax.expression;

import com.passaro.mypallene.syntax.Leaf;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class IntegerConst extends Expr implements Leaf<Integer> {

    private final int value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public IntegerConst(Location leftLocation, Location rightLocation, int value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }

    /**
     * @return The value
     */
    @Override
    public Integer getName() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
