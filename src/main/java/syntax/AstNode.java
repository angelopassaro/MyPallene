package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import visitor.Visitable;

public abstract class AstNode implements Visitable {
    private final Location leftLocation;
    private final Location rightLocation;

    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    public AstNode(Location leftLocation, Location rightLocation) {
        this.leftLocation = leftLocation;
        this.rightLocation = rightLocation;
    }

    /**
     * @return The left position
     */
    public Location getLeftLocation() {
        return leftLocation;
    }

    /**
     * @return The right position
     */
    public Location getRightLocation() {
        return rightLocation;
    }
}
