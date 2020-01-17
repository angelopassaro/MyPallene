package syntax.statement;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Expr;
import syntax.expression.Id;
import visitor.Visitor;

import java.util.LinkedList;

public class ForStatement extends Statement {

    private Expr assignExpr,commaExpr;
    private Id id;
    private LinkedList<Statement> statements;

    /**
     * {@inheritDoc}
     *
     * @param assignExpr The assign expr
     * @param commaExpr  The comma expr
     * @param statements The statement
     */
    public ForStatement(Location leftLocation, Location rightLocation, Id id, Expr assignExpr, Expr commaExpr, LinkedList<Statement> statements) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.assignExpr = assignExpr;
        this.commaExpr = commaExpr;
        this.statements = statements;
    }

    /**
     * @return The assignExpr
     */
    public Expr getAssignExpr() {
        return assignExpr;
    }

    /**
     * @return The commaExpr
     */
    public Expr getCommaExpr() {
        return commaExpr;
    }

    /**
     * @return The statement
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    /**
     *
     * @return The id
     */
    public Id getId() {return id; }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
