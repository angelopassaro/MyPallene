package syntax.expression;

import syntax.Leaf;
import visitor.Visitor;

public class IntegerConst extends Expr implements Leaf<Integer> {

    private final int value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public IntegerConst(int leftLocation, int rightLocation, int value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }

    /**
     * @return The value
     */
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
