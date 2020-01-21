package syntax.function;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.ParDecl;
import syntax.expression.Id;
import syntax.statement.Statement;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;

import java.util.LinkedList;

public class ComplexDefFun extends Function {

    private Id id;
    private TypeDenoter type;
    private LinkedList<Statement> statements;
    private LinkedList<ParDecl> parDecls;

    /**
     * {@inheritDoc}
     *
     * @param id         The id
     * @param type       The function return syntax.type
     * @param statements The statment
     * @param parDecls   The param of function
     */
    public ComplexDefFun(Location leftLocation, Location rightLocation, Id id, TypeDenoter type, LinkedList<Statement> statements, LinkedList<ParDecl> parDecls) {
        super(leftLocation, rightLocation);
        this.id = id;
        this.type = type;
        this.statements = statements;
        this.parDecls = parDecls;
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
     * @return The statment
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    /**
     * @return param
     */
    public LinkedList<ParDecl> getParDecls() {
        return parDecls;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
