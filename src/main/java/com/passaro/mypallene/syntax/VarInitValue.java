package com.passaro.mypallene.syntax;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;


public class VarInitValue extends AstNode {

    private Expr expr;

    /**
     * {@inheritDoc}
     *
     * @param expr The expr
     */
    public VarInitValue(Location leftLocation, Location rightLocation, Expr expr) {
        super(leftLocation, rightLocation);
        this.expr = expr;
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
