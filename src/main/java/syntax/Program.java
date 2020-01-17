package syntax;

import java_cup.runtime.ComplexSymbolFactory.Location;
import syntax.function.Function;
import visitor.Visitor;

import java.util.LinkedList;

public class Program extends AstNode {

    private Global global;
    private LinkedList<Function> functions;

    /**
     * {@inheritDoc}
     *
     * @param global    The list of global
     * @param functions The list of functions
     */
    public Program(Location leftLocation, Location rightLocation, Global global, LinkedList<Function> functions) {
        super(leftLocation, rightLocation);
        this.global = global;
        this.functions = functions;
    }

    /**
     * @return The global
     */
    public Global getGlobal() {
        return global;
    }


    /**
     * @return a list of functions
     */
    public LinkedList<Function> getFunctions() {
        return functions;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }

}
