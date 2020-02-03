package nodetype;

import java.util.List;
import java.util.Objects;

public class CompositeType implements NodeType {

    private final List<NodeType> types;

    public CompositeType(List<NodeType> types) {
        this.types = types;
    }

    @Override
    public PrimitiveNodeType checkAdd(PrimitiveNodeType type) {
        return null;
    }

    @Override
    public PrimitiveNodeType checkSub(PrimitiveNodeType type) {
        return null;
    }

    @Override
    public PrimitiveNodeType checkMul(PrimitiveNodeType type) {
        return null;
    }

    @Override
    public PrimitiveNodeType checkDiv(PrimitiveNodeType type) {
        return null;
    }

    @Override
    public PrimitiveNodeType checkRel(PrimitiveNodeType type) {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.types);
        return hash;
    }

    public void addType(NodeType type) {
        this.types.add(type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            final CompositeType other = (CompositeType) obj;
            return Objects.equals(this.types, other.types);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.types.forEach(t -> sb.append(t.toString()));
        return sb.toString();
    }
}