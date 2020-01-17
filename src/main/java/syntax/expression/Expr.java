package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.AstNode;

public abstract class Expr extends AstNode {

    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    public Expr(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }
}
