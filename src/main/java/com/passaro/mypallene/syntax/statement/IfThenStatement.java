package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class IfThenStatement extends Statement {

    private Expr expr;
    private LinkedList<Statement> statements;


    /**
     * {@inheritDoc}
     *
     * @param expr       The expr
     * @param statements The statement
     */
    public IfThenStatement(Location leftLocation, Location rightLocation, Expr expr, LinkedList<Statement> statements) {
        super(leftLocation, rightLocation);
        this.expr = expr;
        this.statements = statements;
    }


    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    /**
     * @return The statments
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
