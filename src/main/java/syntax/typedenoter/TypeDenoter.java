package syntax.typedenoter;

import java_cup.runtime.ComplexSymbolFactory.Location;
import nodetype.NodeType;
import syntax.AstNode;

public abstract class TypeDenoter extends AstNode {

    public TypeDenoter(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }

    public abstract NodeType typeFactory();

}