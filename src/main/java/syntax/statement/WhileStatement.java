package syntax.statement;

import syntax.expression.Expr;
import visitor.Visitor;

import java.util.LinkedList;


public class WhileStatement extends Statement {
    private Expr expr;
    private LinkedList<Statement> statements;

    /**
     * {@inheritDoc}
     *
     * @param expr       The expr
     * @param statements The statements
     */
    public WhileStatement(int leftLocation,int rightLocation, Expr expr, LinkedList<Statement> statements) {
        super(leftLocation, rightLocation);
        this.expr = expr;
        this.statements = statements;
    }

    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    /**
     * @return The Statement
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
