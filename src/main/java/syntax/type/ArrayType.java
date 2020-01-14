package syntax.type;

import visitor.Visitor;

public class ArrayType extends Type {

    private Type type;

    /**
     * {@inheritDoc}
     *
     * @param type The syntax.type
     */
    public ArrayType(int leftLocation, int rightLocation, Type type) {
        super(leftLocation, rightLocation);
        this.type = type;
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
