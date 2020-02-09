package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.VarDecl;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class LocalStatement extends Statement {

    private LinkedList<VarDecl> varDecls;
    private LinkedList<Statement> statements;

    /**
     * {@inheritDoc}
     *
     * @param varDecls  The varDecl
     * @param statement The statement
     */
    public LocalStatement(Location leftLocation, Location rightLocation, LinkedList<VarDecl> varDecls, LinkedList<Statement> statement) {
        super(leftLocation, rightLocation);
        this.varDecls = varDecls;
        this.statements = statement;
    }

    /**
     * @return The varDecl
     */
    public LinkedList<VarDecl> getVarDecls() {
        return varDecls;
    }

    /**
     * @return The statement
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
