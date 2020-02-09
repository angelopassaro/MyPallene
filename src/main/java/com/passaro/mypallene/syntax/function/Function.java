package com.passaro.mypallene.syntax.function;

import com.passaro.mypallene.syntax.AstNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Function extends AstNode {

    /**
     * Create a new generic AST node.
     * {@inheritDoc}
     */
    public Function(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }

}
