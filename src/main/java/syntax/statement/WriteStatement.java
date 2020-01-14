package syntax.statement;

import syntax.expression.Expr;
import visitor.Visitor;

import java.util.LinkedList;

public class WriteStatement extends Statement {

    private LinkedList<Expr> exprs;

    /**
     * {@inheritDoc}
     *
     * @param exprs The exprs
     */
    public WriteStatement(int leftLocation, int rightLocation, LinkedList<Expr> exprs) {
        super(leftLocation, rightLocation);
        this.exprs = exprs;
    }

    /**
     * @return The exprs
     */
    public LinkedList<Expr> getExprs() {
        return exprs;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
