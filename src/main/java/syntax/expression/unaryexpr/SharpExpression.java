package syntax.expression.unaryexpr;

import syntax.expression.BooleanExp;
import syntax.expression.Expr;
import visitor.Visitor;

public class SharpExpression extends BooleanExp {

    private Expr expr;

    /**
     * {@inheritDoc}
     *
     * @param expr The not
     */
    public SharpExpression(int leftLocation, int rightLocation, Expr expr) {
        super(leftLocation, rightLocation);
        this.expr = expr;
    }

    /**
     * @return The not
     */
    public Expr getExpr() {
        return expr;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
