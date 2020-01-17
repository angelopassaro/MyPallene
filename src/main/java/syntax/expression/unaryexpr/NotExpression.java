package syntax.expression.unaryexpr;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.BooleanExp;
import syntax.expression.Expr;
import visitor.Visitor;

public class NotExpression extends BooleanExp {

    private Expr not;

    /**
     * {@inheritDoc}
     *
     * @param not The not
     */
    public NotExpression(Location leftLocation, Location rightLocation, Expr not) {
        super(leftLocation, rightLocation);
        this.not = not;
    }

    /**
     * @return The not
     */
    public Expr getNot() {
        return not;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
