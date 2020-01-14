package syntax.expression;

import syntax.Leaf;
import visitor.Visitor;

public class BooleanConst extends Expr implements Leaf<Boolean> {

    private boolean value;

    public BooleanConst(int leftLocation, int rightLocation, boolean value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }


    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
