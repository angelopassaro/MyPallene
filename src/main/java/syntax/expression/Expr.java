package syntax.expression;

import syntax.AstNode;

public abstract class Expr extends AstNode {

    /**
     * Create a new generic AST node.
     *
     * @param leftLocation  the left location
     * @param rightLocation the right location
     */
    public Expr(int leftLocation, int rightLocation) {
        super(leftLocation, rightLocation);
    }
}
