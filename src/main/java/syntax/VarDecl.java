package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;

public class VarDecl extends AstNode {

    private Variable variable;
    private TypeDenoter type;
    private VarInitValue varInitValue;

    /**
     * {@inheritDoc}
     *
     * @param variable     The id
     * @param type         The syntax.type of variable
     * @param varInitValue The initial value of variable
     */
    public VarDecl(Location leftLocation, Location rightLocation, Variable variable, TypeDenoter type, VarInitValue varInitValue) {
        super(leftLocation, rightLocation);
        this.variable = variable;
        this.type = type;
        this.varInitValue = varInitValue;
    }

    /**
     * @return The id
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @return The syntax.type
     */
    public TypeDenoter getTypeDenoter() {
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
