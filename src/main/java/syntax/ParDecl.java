package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;

public class ParDecl extends AstNode {

    private Variable variable;
    private TypeDenoter type;

    /**
     * {@inheritDoc}
     *
     * @param variable The variable
     * @param type     The param syntax.type
     */
    public ParDecl(Location leftLocation, Location rightLocation, Variable variable, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.variable = variable;
        this.type = type;
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
    public TypeDenoter getTypeDenoterDenoter() {
        return type;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
