package syntax;

import visitor.Visitable;

public abstract class AstNode implements Visitable {
    private final int leftLocation;
    private final int rightLocation;

    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    public AstNode(int leftLocation, int rightLocation) {
        this.leftLocation = leftLocation;
        this.rightLocation = rightLocation;
    }

    /**
     * @return The left position
     */
    public int getLeftLocation() {
        return leftLocation;
    }

    /**
     * @return The right position
     */
    public int getRightLocation() {
        return rightLocation;
    }
}
