package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.type.PrimitiveType;

/**
 * This node wrapping the primitive types of a YASPL program.
 */
public class TypeDenoter extends AstNode implements Leaf<String> {

    private final String kind;

    /**
     * {@inheritDoc}
     *
     * @param kind the type name
     */
    public TypeDenoter(Location leftLocation, Location rightLocation, String kind) {
        super(leftLocation, rightLocation);
        this.kind = kind;
    }

    /**
     * Create the PrimitiveType associated with this instance
     *
     * @return the primitive type
     */
    public PrimitiveType typeFactory() {
        switch (this.kind) {
            case "int":
                return PrimitiveType.INT;
            case "double":
                return PrimitiveType.FLOAT;
            case "string":
                return PrimitiveType.STRING;
            case "bool":
                return PrimitiveType.BOOL;
            default:
                return PrimitiveType.NULL;
        }
    }

    @Override
    public String getValue() {
        return this.kind;
    }

}