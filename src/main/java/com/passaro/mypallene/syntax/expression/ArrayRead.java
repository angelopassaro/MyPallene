package com.passaro.mypallene.syntax.expression;

import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class ArrayRead extends Expr {

    private Expr arrayName, arrayElement;

    /**
     * {@inheritDoc}
     *
     * @param arrayName    The arrayName
     * @param arrayElement The arrayElement
     */
    public ArrayRead(Location leftLocation, Location rightLocation, Expr arrayName, Expr arrayElement) {
        super(leftLocation, rightLocation);
        this.arrayName = arrayName;
        this.arrayElement = arrayElement;
    }

    /**
     * @return The arrayName
     */
    public Expr getArrayName() {
        return arrayName;
    }

    /**
     * @return The arrayElement
     */
    public Expr getArrayElement() {
        return arrayElement;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
