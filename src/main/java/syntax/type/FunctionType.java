package syntax.type;

import visitor.Visitor;

import java.util.LinkedList;

public class FunctionType extends Type {

    private LinkedList<Type> types;
    private Type type;

    /**
     * {@inheritDoc}
     *
     * @param types The input types
     * @param type  The return type
     */
    public FunctionType(int leftLocation, int rightLocation, LinkedList<Type> types, Type type) {
        super(leftLocation, rightLocation);
        this.types = types;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     *
     * @param type The return type
     */
    public FunctionType(int leftLocation, int rightLocation, Type type) {
        super(leftLocation, rightLocation);
        this.types = new LinkedList<>();
        this.type = type;
    }

    /**
     * @return The types
     */
    public LinkedList<Type> getTypes() {
        return types;
    }

    /**
     * @return The type
     */
    public Type getType() {
        return type;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
