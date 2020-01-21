package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.expression.Id;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;

public class ParDecl extends AstNode {

    private Id id;
    private TypeDenoter type;

    /**
     * {@inheritDoc}
     *
     * @param id   The id
     * @param type The param syntax.type
     */
    public ParDecl(Location leftLocation, Location rightLocation, Id id, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.type = type;
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

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
