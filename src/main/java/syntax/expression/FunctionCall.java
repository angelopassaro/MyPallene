package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import visitor.Visitor;

import java.util.LinkedList;

public class FunctionCall extends Expr {

    private Id id;
    private LinkedList<Expr> exprs;

    /**
     * {@inheritDoc}
     *
     * @param id    The id
     * @param exprs The exprs
     */
    public FunctionCall(Location leftLocation, Location rightLocation, Id id, LinkedList<Expr> exprs) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.exprs = exprs;
    }

    /**
     * {@inheritDoc}
     *
     * @param id The id
     */
    public FunctionCall(Location leftLocation, Location rightLocation, Id id) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.exprs = new LinkedList<>();
    }

    /**
     * @return The id
     */
    public Id getId() {
        return id;
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
