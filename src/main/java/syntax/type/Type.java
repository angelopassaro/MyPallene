package syntax.type;

import syntax.AstNode;

public abstract class Type extends AstNode {

    public Type(int leftLocation, int rightLocation) {
        super(leftLocation, rightLocation);
    }
}
