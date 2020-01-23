package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Expr;
import visitor.Visitor;

public class Variable extends Expr implements Leaf<String> {

    private String name;

    /**
     * @param leftLocation  The left location
     * @param rightLocation The right location
     * @param name          The id
     */

    public Variable(Location leftLocation, Location rightLocation, String name) {
        super(leftLocation, rightLocation);
        this.name = name;
    }

    /**
     * @return The name
     */
    @Override
    public String getValue() {
        return name;
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}