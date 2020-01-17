package syntax.expression.unaryexpr;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Expr;
import visitor.Visitor;

public class UMinusExpression extends Expr {

    private Expr minus;

    /**
     * {@inheritDoc}
     *
     * @param minus The minus
     */
    public UMinusExpression(Location leftLocation, Location rightLocation, Expr minus) {
        super(leftLocation, rightLocation);
        this.minus = minus;
    }

    /**
     * @return The minus
     */
    public Expr getMinus() {
        return minus;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
