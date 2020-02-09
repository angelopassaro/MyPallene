package com.passaro.mypallene.semantic;

import com.passaro.mypallene.nodekind.NodeKind;
import com.passaro.mypallene.nodetype.NodeType;

public class SymbolTableRecord {

    private NodeType type;
    private NodeKind kind;

    public SymbolTableRecord(NodeType type, NodeKind kind) {
        this.type = type;
        this.kind = kind;
    }

    public NodeType getTypeDenoter() {
        return type;
    }

    public NodeKind getKind() {
        return kind;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public void setType(NodeKind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "SymbolTableRecord{" +
                "kind='" + kind + '\'' +
                ", type=" + type + '}';
    }
}
