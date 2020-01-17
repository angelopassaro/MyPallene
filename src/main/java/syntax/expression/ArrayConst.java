package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.type.Type;
import visitor.Visitor;



public class ArrayConst extends Expr{

    private Type type;

    public ArrayConst(Location leftLocation, Location rightLocation, Type type) {
        super(leftLocation, rightLocation);
        this.type = type;
    }

    public Type getType() {
        return type;
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
