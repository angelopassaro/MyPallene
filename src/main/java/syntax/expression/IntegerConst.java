package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.Leaf;
import visitor.Visitor;

public class IntegerConst extends Expr implements Leaf<Integer> {

    private final int value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public IntegerConst(Location leftLocation, Location rightLocation, int value) {
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
