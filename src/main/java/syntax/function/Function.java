package syntax.function;

import syntax.AstNode;

public abstract class Function extends AstNode {

    /**
     * Create a new generic AST node.
     * {@inheritDoc}
     */
    public Function(int leftLocation, int rightLocation) {
        super(leftLocation, rightLocation);
    }

}
