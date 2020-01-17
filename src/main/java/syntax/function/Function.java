package syntax.function;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.AstNode;

public abstract class Function extends AstNode {

    /**
     * Create a new generic AST node.
     * {@inheritDoc}
     */
    public Function(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }

}
