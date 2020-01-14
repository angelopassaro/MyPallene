package syntax;

import syntax.expression.Id;
import syntax.type.Type;
import visitor.Visitor;

public class ParDecl extends AstNode {

    private Id id;
    private Type type;

    /**
     * {@inheritDoc}
     *
     * @param id   The id
     * @param type The param syntax.type
     */
    public ParDecl(int leftLocation, int rightLocation, Id id, Type type) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.type = type;
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

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
