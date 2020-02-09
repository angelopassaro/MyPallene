package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;


public class ReturnStatement extends Statement {

    private Expr expr;


    /**
     * {@inheritDoc}
     *
     * @param exprs The expr
     */
    public ReturnStatement(Location leftLocation, Location rightLocation, Expr exprs) {
        super(leftLocation, rightLocation);
        this.expr = exprs;
    }

    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
