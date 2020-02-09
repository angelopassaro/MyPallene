package com.passaro.mypallene.syntax.statement;

import com.passaro.mypallene.syntax.AstNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Statement extends AstNode {

    /**
     * {@inheritDoc}
     */
    public Statement(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }
}
