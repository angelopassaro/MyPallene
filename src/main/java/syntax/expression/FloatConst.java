package syntax.expression;

import syntax.Leaf;
import visitor.Visitor;

public class FloatConst extends Expr implements Leaf<Float> {

    private final float value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public FloatConst(int leftLocation, int rightLocation, float value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }

    /**
     * @return The value
     */
    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
