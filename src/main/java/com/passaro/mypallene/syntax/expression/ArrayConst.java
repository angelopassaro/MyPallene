package com.passaro.mypallene.syntax.expression;

import com.passaro.mypallene.syntax.typedenoter.TypeDenoter;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;



public class ArrayConst extends Expr{

    private TypeDenoter type;

    public ArrayConst(Location leftLocation, Location rightLocation, TypeDenoter type) {
        super(leftLocation, rightLocation);
        this.type = type;
    }

    public TypeDenoter getTypeDenoter() {
        return type;
    }


    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}
