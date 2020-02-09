package com.passaro.mypallene.syntax.expression.unaryexpr;

import com.passaro.mypallene.syntax.expression.BooleanExp;
import com.passaro.mypallene.syntax.expression.Expr;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class NotExpression extends BooleanExp {

    private Expr not;

    /**
     * {@inheritDoc}
     *
     * @param not The not
     */
    public NotExpression(Location leftLocation, Location rightLocation, Expr not) {
        super(leftLocation, rightLocation);
        this.not = not;
    }

    /**
     * @return The not
     */
    public Expr getNot() {
        return not;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
