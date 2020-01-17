package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import visitor.Visitor;

public class ArrayRead extends Expr {

    private Expr arrayName, arrayElement;

    /**
     * {@inheritDoc}
     *
     * @param arrayName    The arrayName
     * @param arrayElement The arrayElement
     */
    public ArrayRead(Location leftLocation, Location rightLocation, Expr arrayName, Expr arrayElement) {
        super(leftLocation, rightLocation);
        this.arrayName = arrayName;
        this.arrayElement = arrayElement;
    }

    /**
     * @return The arrayName
     */
    public Expr getArrayName() {
        return arrayName;
    }

    /**
     * @return The arrayElement
     */
    public Expr getArrayElement() {
        return arrayElement;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
