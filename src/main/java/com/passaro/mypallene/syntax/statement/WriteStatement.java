package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class WriteStatement extends Statement {

    private LinkedList<Expr> exprs;

    /**
     * {@inheritDoc}
     *
     * @param exprs The exprs
     */
    public WriteStatement(Location leftLocation, Location rightLocation, LinkedList<Expr> exprs) {
        super(leftLocation, rightLocation);
        this.exprs = exprs;
    }

    /**
     * @return The exprs
     */
    public LinkedList<Expr> getExprs() {
        return exprs;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
