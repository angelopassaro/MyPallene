package com.passaro.mypallene.syntax.expression;

import com.passaro.mypallene.syntax.Leaf;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class NilConst extends Expr implements Leaf<Object> {

    public NilConst(Location leftLocation, Location rightLocation) {
        super(leftLocation, rightLocation);
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public Object getName() {
        return null;
    }
}
