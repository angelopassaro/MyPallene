package nodetype;


public interface NodeType {

    PrimitiveType checkAdd(PrimitiveType type);

    PrimitiveType checkSub(PrimitiveType type);

    PrimitiveType checkMul(PrimitiveType type);

    PrimitiveType checkDiv(PrimitiveType type);
}
