package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.Leaf;
import visitor.Visitor;

public class StringConst extends Expr implements Leaf<String> {

    private final String value;

    /**
     * {@inheritDoc}
     *
     * @param value The value
     */
    public StringConst(Location leftLocation, Location rightLocation, String value) {
        super(leftLocation, rightLocation);
        this.value = value;
    }

    /**
     * @return The value
     */
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
