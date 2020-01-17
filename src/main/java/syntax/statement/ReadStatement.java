package syntax.statement;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Id;
import visitor.Visitor;

import java.util.LinkedList;

public class ReadStatement extends Statement {

    private LinkedList<Id> ids;

    /**
     * {@inheritDoc}
     *
     * @param ids The id
     */
    public ReadStatement(Location leftLocation, Location rightLocation, LinkedList<Id> ids) {
        super(leftLocation, rightLocation);
        this.ids = ids;
    }

    /**
     * @return The id
     */
    public LinkedList<Id> getIds() {
        return ids;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
