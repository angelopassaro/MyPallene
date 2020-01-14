package syntax.statement;

import syntax.AstNode;

public abstract class Statement extends AstNode {

    /**
     * {@inheritDoc}
     */
    public Statement(int leftLocation, int rightLocation) {
        super(leftLocation, rightLocation);
    }
}
