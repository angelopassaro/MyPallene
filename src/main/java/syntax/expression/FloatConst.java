package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.Leaf;
import visitor.Visitor;

public class FloatConst extends Expr implements Leaf<Float> {

    private final float value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public FloatConst(Location leftLocation, Location rightLocation, float value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }

    /**
     * @return The value
     */
    @Override
    public Float getName() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
