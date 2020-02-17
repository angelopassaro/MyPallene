package com.passaro.mypallene.syntax.typedenoter;

import com.passaro.mypallene.nodetype.PrimitiveNodeType;
import com.passaro.mypallene.syntax.Leaf;
import com.passaro.mypallene.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;


public class PrimitiveTypeDenoter extends TypeDenoter implements Leaf<String> {


    private String kind;

    public PrimitiveTypeDenoter(Location leftLocation, Location rightLocation, String kind) {
        super(leftLocation, rightLocation);
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public String cType() {
        switch (this.kind) {
            case "INT":
                return "int";
            case "FLOAT":
                return "float";
            case "STRING":
                return "char *";
            case "BOOL":
                return "bool";
            default:
                return "undefined";
        }

    }


    public PrimitiveNodeType typeFactory() {
        switch (this.kind) {
            case "INT":
                return PrimitiveNodeType.INT;
            case "FLOAT":
                return PrimitiveNodeType.FLOAT;
            case "STRING":
                return PrimitiveNodeType.STRING;
            case "BOOL":
                return PrimitiveNodeType.BOOL;
            default:
                return PrimitiveNodeType.NULL;
        }
    }

    @Override
    public String getName() {
        return this.kind;
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P arg) {
        return visitor.visit(this, arg);
    }
}