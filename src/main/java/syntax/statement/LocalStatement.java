package syntax.statement;

import syntax.VarDecl;
import visitor.Visitor;

import java.util.LinkedList;

public class LocalStatement extends Statement {

    private LinkedList<VarDecl> varDecls;
    private LinkedList<Statement> statements;

    /**
     * {@inheritDoc}
     *
     * @param varDecls   The varDecl
     * @param statement The statement
     */
    public LocalStatement(int leftLocation, int rightLocation, LinkedList<VarDecl> varDecls, LinkedList<Statement> statement) {
        super(leftLocation, rightLocation);
        this.varDecls = varDecls;
        this.statements = statement;
    }

    /**
     * @return The varDecl
     */
    public LinkedList<VarDecl> getVarDecls() {
        return varDecls;
    }

    /**
     * @return The statement
     */
    public LinkedList<Statement> getStatements() {
        return statements;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
