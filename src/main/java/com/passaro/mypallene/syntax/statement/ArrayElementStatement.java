package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class ArrayElementStatement extends Statement {

    private Expr arrayExpr, arrayPoint, arrayAssign;

    /**
     * {@inheritDoc}
     *
     * @param arrayExpr   The array expr
     * @param arrayPoint  The array point
     * @param arrayAssign The array assign
     */
    public ArrayElementStatement(Location leftLocation, Location rightLocation, Expr arrayExpr, Expr arrayPoint, Expr arrayAssign) {
        super(leftLocation, rightLocation);
        this.arrayExpr = arrayExpr;
        this.arrayPoint = arrayPoint;
        this.arrayAssign = arrayAssign;
    }

    /**
     * @return The arrayExpr
     */
    public Expr getArrayExpr() {
        return arrayExpr;
    }

    /**
     * @return The arrayPoint
     */
    public Expr getArrayPoint() {
        return arrayPoint;
    }

    /**
     * @return The arrayAssign
     */
    public Expr getArrayAssign() {
        return arrayAssign;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
