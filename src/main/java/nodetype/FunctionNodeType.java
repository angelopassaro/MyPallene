package nodetype;

import java.util.Objects;

public class FunctionNodeType implements NodeType {

    CompositeNodeType input;
    NodeType output;

    public FunctionNodeType(CompositeNodeType input, NodeType output) {
        this.input = input;
        this.output = output;
    }

    public CompositeNodeType getInput() {
        return input;
    }

    public NodeType getOutput() {
        return output;
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
        hash = 11 * hash + Objects.hashCode(this.input) + Objects.hashCode(this.output);
        return hash;
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
            final FunctionNodeType other = (FunctionNodeType) obj;
            return Objects.equals(this.output, other.output) && Objects.equals(this.input, other.input);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.input.toString());
        sb.append("->");
        sb.append(this.input.toString());
        return sb.toString();
    }
}
