package syntax.statement;

import syntax.expression.Expr;
import syntax.expression.Id;
import visitor.Visitor;

public class AssignStatement extends Statement {

    private Expr expr;
    private Id id;

    /**
     * {@inheritDoc}
     *
     * @param expr The expr
     */
    public AssignStatement(int leftLocation, int rightLocation,Id id, Expr expr) {
        super(leftLocation, rightLocation);
        this.expr = expr;
        this.id = id;
    }

    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    /**
     *
     * @return The Id
     */
    public Id getId(){ return id;}

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
