package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import visitor.Visitor;

import java.util.LinkedList;

public class Global extends AstNode {

    private LinkedList<VarDecl> varDecls;

    /**
     * {@inheritDoc}
     *
     * @param varDecls The list of varDecls
     */
    public Global(Location leftLocation, Location rightLocation, LinkedList<VarDecl> varDecls) {
        super(leftLocation, rightLocation);
        this.varDecls = varDecls;
    }


    /**
     * @return The list of VarDecl
     */
    public LinkedList<VarDecl> getVarDecls() {
        return varDecls;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }


}
