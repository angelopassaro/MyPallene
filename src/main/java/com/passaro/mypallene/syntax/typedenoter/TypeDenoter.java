package com.passaro.mypallene.syntax.typedenoter;

import com.passaro.mypallene.nodetype.NodeType;
import com.passaro.mypallene.syntax.AstNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class TypeDenoter extends AstNode {

    public TypeDenoter(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }

    public abstract NodeType typeFactory();

}