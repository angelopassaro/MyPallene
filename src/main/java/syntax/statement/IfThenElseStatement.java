package syntax.statement;

import syntax.expression.Expr;
import visitor.Visitor;

import java.util.LinkedList;


public class IfThenElseStatement extends Statement {

    private Expr expr;
    private LinkedList<Statement> thenStatement, elseStatement;

    /**
     * {@inheritDoc}
     *
     * @param expr          The expr
     * @param thenStatement The then Statement
     * @param elseStatement The else statement
     */
    public IfThenElseStatement(int leftLocation, int rightLocation, Expr expr, LinkedList<Statement> thenStatement, LinkedList<Statement> elseStatement) {
        super(leftLocation, rightLocation);
        this.expr = expr;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    /**
     * @return The expr
     */
    public Expr getExpr() {
        return expr;
    }

    /**
     * @return The ThenStatement
     */
    public LinkedList<Statement> getThenStatement() {
        return thenStatement;
    }

    /**
     * @return The ElseStatement
     */
    public LinkedList<Statement> getElseStatement() {
        return elseStatement;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
