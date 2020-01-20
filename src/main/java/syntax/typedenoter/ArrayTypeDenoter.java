package syntax.typedenoter;

import java_cup.runtime.ComplexSymbolFactory.Location;
import nodetype.PrimitiveNodeType;
import visitor.Visitor;


public class ArrayTypeDenoter extends TypeDenoter {

    private TypeDenoter type;

    /**
     * {@inheritDoc}
     *
     * @param type The syntax.type
     */
    public ArrayTypeDenoter(Location leftLocation, Location rightLocation, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.type = type;
    }

    /**
     * @return The syntax.type
     */
    public TypeDenoter getType() {
        return type;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public PrimitiveNodeType typeFactory() {
        return null;
    }
}
