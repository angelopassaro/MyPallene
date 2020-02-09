package com.passaro.mypallene.syntax.expression.binaryexpr.arithop;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class TimesOp extends Expr {

    private Expr element1, element2;

    /**
     * {@inheritDoc}
     *
     * @param element1 The element1
     * @param element2 The element2
     */
    public TimesOp(Location leftLocation, Location rightLocation, Expr element1, Expr element2) {
        super(leftLocation, rightLocation);
        this.element1 = element1;
        this.element2 = element2;
    }

    /**
     * @return The element1
     */
    public Expr getElement1() {
        return element1;
    }

    /**
     * @return The element2
     */
    public Expr getElement2() {
        return element2;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
