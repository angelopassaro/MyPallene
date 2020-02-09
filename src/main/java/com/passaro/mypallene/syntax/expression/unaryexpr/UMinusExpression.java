package com.passaro.mypallene.syntax.expression.unaryexpr;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class UMinusExpression extends Expr {

    private Expr minus;

    /**
     * {@inheritDoc}
     *
     * @param minus The minus
     */
    public UMinusExpression(Location leftLocation, Location rightLocation, Expr minus) {
        super(leftLocation, rightLocation);
        this.minus = minus;
    }

    /**
     * @return The minus
     */
    public Expr getMinus() {
        return minus;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
