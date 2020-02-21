package com.passaro.mypallene.syntax.expression;

import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class Execute extends Expr {

    private LinkedList<Expr> exprs;
    private Id id;


    public Execute(Location leftLocation, Location rightLocation, Id id, LinkedList<Expr> exprs) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.exprs = exprs;
    }

    public Id getId() {
        return id;
    }

    public LinkedList<Expr> getExprs() {
        return exprs;
    }

    public void setExprs(LinkedList<Expr> exprs) {
        this.exprs = exprs;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
