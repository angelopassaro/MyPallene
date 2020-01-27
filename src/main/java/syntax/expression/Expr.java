package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import nodetype.NodeType;
import syntax.AstNode;

public abstract class Expr extends AstNode {

    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    private NodeType type;

    public Expr(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
