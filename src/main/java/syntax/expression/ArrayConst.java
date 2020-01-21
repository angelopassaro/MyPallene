package syntax.expression;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;



public class ArrayConst extends Expr{

    private TypeDenoter type;

    public ArrayConst(Location leftLocation, Location rightLocation, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.type = type;
    }

    public TypeDenoter getTypeDenoterDenoter() {
        return type;
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
