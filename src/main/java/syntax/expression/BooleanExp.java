package syntax.expression;

public abstract class BooleanExp extends Expr {
    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    public BooleanExp(int leftLocation, int rightLocation) {
        super(leftLocation, rightLocation);
    }
}
