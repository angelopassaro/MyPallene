package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class BooleanExp extends Expr {
    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    public BooleanExp(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }
}
