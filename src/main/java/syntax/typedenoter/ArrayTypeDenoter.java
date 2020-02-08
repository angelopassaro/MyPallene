package syntax.typedenoter;

import java_cup.runtime.ComplexSymbolFactory.Location;
import nodetype.ArrayNodeType;
import nodetype.NodeType;
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
    public TypeDenoter getTypeDenoter() {
        return type;
    }

    /**
     * @return type of element
     */
    public NodeType getElementsType() {
        return this.type.typeFactory();
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public NodeType typeFactory() {
        return new ArrayNodeType((PrimitiveNodeType) this.type.typeFactory());
    }

    public String cType() {
        switch ((PrimitiveNodeType) this.getElementsType()) {
            case STRING:
                return "Char *";
            case INT:
                return "int";
            case FLOAT:
                return "float";
            case BOOL:
                return "bool";
            case NULL:
                return "";
            default:
                return null;
        }
    }
}
