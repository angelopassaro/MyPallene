package com.passaro.mypallene.syntax.function;

import com.passaro.mypallene.syntax.Variable;
import com.passaro.mypallene.syntax.statement.Statement;
import com.passaro.mypallene.syntax.typedenoter.TypeDenoter;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;

public class SimpleDefFun extends Function {

    private Variable variable;
    private TypeDenoter type;
    private LinkedList<Statement> statements;

    /**
     * @param leftLocation  The left location
     * @param rightLocation The right location
     * @param variable      The id
     * @param type          The function return com.passaro.mypallene.syntax.type
     * @param statements    The statements
     */
    public SimpleDefFun(Location leftLocation, Location rightLocation, Variable variable, TypeDenoter type, LinkedList<Statement> statements) {
        super(leftLocation, rightLocation);
        this.variable = variable;
        this.type = type;
        this.statements = statements;
    }

    /**
     * @return The id
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @return The com.passaro.mypallene.syntax.type
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

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
