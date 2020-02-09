package com.passaro.mypallene.syntax.expression.unaryexpr;

import com.passaro.mypallene.syntax.expression.BooleanExp;
import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class SharpExpression extends BooleanExp {

    private Expr expr;

    /**
     * {@inheritDoc}
     *
     * @param expr The not
     */
    public SharpExpression(Location leftLocation, Location rightLocation, Expr expr) {
        super(leftLocation, rightLocation);
        this.expr = expr;
    }

    /**
     * @return The not
     */
    public Expr getExpr() {
        return expr;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
