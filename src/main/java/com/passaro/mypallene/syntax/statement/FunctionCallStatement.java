package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.syntax.expression.Id;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class FunctionCallStatement extends Statement {
    private Id id;
    private LinkedList<Expr> exprs;

    /**
     * {@inheritDoc}
     *
     * @param id    The id
     * @param exprs The expr
     */
    public FunctionCallStatement(Location leftLocation, Location rightLocation, Id id, LinkedList<Expr> exprs) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.exprs = exprs;
    }

    /**
     * {@inheritDoc}
     *
     * @param id The id
     */
    public FunctionCallStatement(Location leftLocation, Location rightLocation, Id id) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.exprs = new LinkedList<>();
    }

    /**
     * @return The id
     */
    public Id getId() {
        return id;
    }

    /**
     * @return The expr
     */
    public LinkedList<Expr> getExprs() {
        return exprs;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
