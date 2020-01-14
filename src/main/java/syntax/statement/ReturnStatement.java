package syntax.statement;

import syntax.expression.Expr;
import visitor.Visitor;


public class ReturnStatement extends Statement {

    private Expr expr;


    /**
     * {@inheritDoc}
     *
     * @param exprs The expr
     */
    public ReturnStatement(int leftLocation, int rightLocation, Expr exprs) {
        super(leftLocation, rightLocation);
        this.expr = exprs;
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
