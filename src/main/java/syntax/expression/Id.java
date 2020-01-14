package syntax.expression;

import syntax.Leaf;
import visitor.Visitor;

public class Id extends Expr implements Leaf<String> {

    private String name;

    /**
     * @param leftLocation  The left location
     * @param rightLocation The right location
     * @param name          The id
     */
    public Id(int leftLocation, int rightLocation, String name) {
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
