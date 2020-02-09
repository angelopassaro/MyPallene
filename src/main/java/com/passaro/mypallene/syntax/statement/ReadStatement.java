package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.expression.Id;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class ReadStatement extends Statement {

    private LinkedList<Id> ids;

    /**
     * {@inheritDoc}
     *
     * @param ids The id
     */
    public ReadStatement(Location leftLocation, Location rightLocation, LinkedList<Id> ids) {
        super(leftLocation, rightLocation);
        this.ids = ids;
    }

    /**
     * @return The id
     */
    public LinkedList<Id> getIds() {
        return ids;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
