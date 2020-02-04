package syntax.function;

import java_cup.runtime.ComplexSymbolFactory.Location;
import nodetype.CompositeNodeType;
import syntax.ParDecl;
import syntax.Variable;
import syntax.statement.Statement;
import syntax.typedenoter.TypeDenoter;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.LinkedList;

public class ComplexDefFun extends Function {

    private Variable variable;
    private TypeDenoter type;
    private LinkedList<Statement> statements;
    private LinkedList<ParDecl> parDecls;

    /**
     * {@inheritDoc}
     *
     * @param variable   The id
     * @param type       The function return syntax.type
     * @param statements The statment
     * @param parDecls   The param of function
     */
    public ComplexDefFun(Location leftLocation, Location rightLocation, Variable variable, TypeDenoter type, LinkedList<Statement> statements, LinkedList<ParDecl> parDecls) {
        super(leftLocation, rightLocation);
        this.variable = variable;
        this.type = type;
        this.statements = statements;
        this.parDecls = parDecls;
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


    /**
     * @return The list of type
     */
    public CompositeNodeType domain() {
        CompositeNodeType ct = new CompositeNodeType(new ArrayList<>());
        this.parDecls.forEach(pd -> ct.addType(pd.getTypeDenoter().typeFactory()));
        return ct;
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
