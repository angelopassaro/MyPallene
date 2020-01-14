package syntax.expression;

import syntax.type.Type;
import visitor.Visitor;



public class ArrayConst extends Expr{

    private Type type;

    public ArrayConst(int leftLocation, int rightLocation, Type type) {
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
