package syntax.statement;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Expr;
import syntax.expression.Id;
import visitor.Visitor;

import java.util.LinkedList;

public class FunctionCallStatement extends Statement {
    private Id id;
    private LinkedList<Expr> exprs;

    /**
     * {@inheritDoc}
     *
     * @param id    The id
     * @param exprs The expr
     */
    public FunctionCallStatement(Location leftLocation, Location rightLocation, Id id, LinkedList<Expr> exprs) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.exprs = exprs;
    }

    /**
     * @return The id
     */
    public Id getId() {
        return id;
    }

    /**
     * @return The expr
     */
    public LinkedList<Expr> getExprs() {
        return exprs;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
