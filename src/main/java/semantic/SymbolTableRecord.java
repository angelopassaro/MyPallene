package semantic;

public class SymbolTableRecord {

    private String type;
    private NodeKind kind;

    public SymbolTableRecord(String type, NodeKind kind) {
        this.type = type;
        this.kind = kind;
    }

    public String getType() {
        return type;
    }

    public NodeKind getKind() {
        return kind;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(NodeKind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "SymbolTableRecord{" +
                "kind='" + kind + '\'' +
                ", type=" + type +
                '}';
    }
}
