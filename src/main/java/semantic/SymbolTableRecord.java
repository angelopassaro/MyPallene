package semantic;

import nodekind.NodeKind;
import nodetype.CompositeNodeType;
import nodetype.NodeType;

import java.util.ArrayList;

public class SymbolTableRecord {

    private NodeType type;
    private NodeKind kind;
    private CompositeNodeType compositeNodeType = new CompositeNodeType(new ArrayList<>());

    public SymbolTableRecord(NodeType type, NodeKind kind, CompositeNodeType compositeNodeType) {
        this.type = type;
        this.kind = kind;
        this.compositeNodeType = compositeNodeType;
    }

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

    public CompositeNodeType getCompositeNodeType() {
        return compositeNodeType;
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
                ", type=" + type + '\'' +
                ", compositeType=" + compositeNodeType +
                '}';
    }
}
