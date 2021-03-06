package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.syntax.expression.Id;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AssignStatement extends Statement {

    private Expr expr;
    private Id id;

    /**
     * {@inheritDoc}
     *
     * @param expr The expr
     */
    public AssignStatement(Location leftLocation, Location rightLocation, Id id, Expr expr) {
        super(leftLocation, rightLocation);
        this.expr = expr;
        this.id = id;
    }

    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    /**
     *
     * @return The Id
     */
    public Id getId(){ return id;}

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
