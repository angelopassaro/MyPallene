package syntax.typedenoter;

import java_cup.runtime.ComplexSymbolFactory.Location;
import nodetype.PrimitiveNodeType;
import visitor.Visitor;

import java.util.LinkedList;


public class FunctionTypeDenoter extends TypeDenoter {

    private LinkedList<TypeDenoter> typeDenoters;
    private TypeDenoter returnTypeDenoter;

    public FunctionTypeDenoter(Location leftLocation, Location rightLocation, LinkedList<TypeDenoter> typeDenoters, TypeDenoter returnTypeDenoter) {
        super(leftLocation, rightLocation);
        this.returnTypeDenoter = returnTypeDenoter;
        this.typeDenoters = typeDenoters;
    }

    public FunctionTypeDenoter(Location leftLocation, Location rightLocation, TypeDenoter returnTypeDenoter) {
        super(leftLocation, rightLocation);
        this.returnTypeDenoter = returnTypeDenoter;
        this.typeDenoters = new LinkedList<>();
    }

    /**
     * @return the returnType
     */
    public TypeDenoter getReturnType() {
        return returnTypeDenoter;
    }

    /**
     * @return the types
     */
    public LinkedList<TypeDenoter> getTypeDenoters() {
        return typeDenoters;
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
