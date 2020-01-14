package syntax.function;

import syntax.expression.Id;
import syntax.statement.Statement;
import syntax.type.Type;
import visitor.Visitor;

import java.util.LinkedList;

public class SimpleDefFun extends Function {

    private Id id;
    private Type type;
    private LinkedList<Statement> statements;

    /**
     * @param leftLocation  The left location
     * @param rightLocation The right location
     * @param id            The id
     * @param type          The function return syntax.type
     * @param statements    The statements
     */
    public SimpleDefFun(int leftLocation, int rightLocation, Id id, Type type, LinkedList<Statement> statements) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.type = type;
        this.statements = statements;
    }

    /**
     * @return The id
     */
    public Id getId() {
        return id;
    }

    /**
     * @return The syntax.type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The statment
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
