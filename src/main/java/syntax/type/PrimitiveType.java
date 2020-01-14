package syntax.type;

import visitor.Visitor;

public class PrimitiveType extends Type {

    private String kind;

    /**
     * {@inheritDoc}
     *
     * @param kind The syntax.type
     */
    public PrimitiveType(int leftLocation, int rightLocation, String kind) {
        super(leftLocation, rightLocation);
        this.kind = kind;
    }

    /**
     * @return The syntax.type
     */
    public String getKind() {
        return kind;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
