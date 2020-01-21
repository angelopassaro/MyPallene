package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Id;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;

public class VarDecl extends AstNode {

    private Id id;
    private TypeDenoter type;
    private VarInitValue varInitValue;

    /**
     * {@inheritDoc}
     *
     * @param id           The id
     * @param type         The syntax.type of variable
     * @param varInitValue The initial value of variable
     */
    public VarDecl(Location leftLocation, Location rightLocation, Id id, TypeDenoter type, VarInitValue varInitValue) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.type = type;
        this.varInitValue = varInitValue;
    }

    /**
     * @return The id
     */
    public Id getId() {
        return id;
    }

    /**
     * @return The syntax.type
     */
    public TypeDenoter getTypeDenoterDenoter() {
        return type;
    }

    /**
     * @return The initial value
     */
    public VarInitValue getVarInitValue() {
        return varInitValue;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
