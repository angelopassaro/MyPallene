package syntax;

import syntax.expression.Expr;
import visitor.Visitor;

public class VarInitValue extends AstNode {

    private Expr expr;

    /**
     * {@inheritDoc}
     *
     * @param expr The expr
     */
    public VarInitValue(int leftLocation, int rightLocation, Expr expr) {
        super(leftLocation, rightLocation);
        this.expr = expr;
    }

    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
