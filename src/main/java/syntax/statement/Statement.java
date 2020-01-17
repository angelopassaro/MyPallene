package syntax.statement;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.AstNode;

public abstract class Statement extends AstNode {

    /**
     * {@inheritDoc}
     */
    public Statement(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }
}
