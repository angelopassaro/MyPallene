package nodetype;


public enum PrimitiveNodeType implements NodeType {

    INT {
        @Override
        public PrimitiveNodeType checkAdd(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkSub(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkMul(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkDiv(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

    },

    FLOAT {
        @Override
        public PrimitiveNodeType checkAdd(PrimitiveNodeType type) {
            switch (type) {
                case INT:
                case FLOAT:
                case BOOL:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkSub(PrimitiveNodeType type) {
            switch (type) {
                case INT:
                case FLOAT:
                case BOOL:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkMul(PrimitiveNodeType type) {
            switch (type) {
                case INT:
                case FLOAT:
                case BOOL:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkDiv(PrimitiveNodeType type) {
            switch (type) {
                case INT:
                case FLOAT:
                case BOOL:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

    },
    BOOL {
        @Override
        public PrimitiveNodeType checkAdd(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkSub(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkMul(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

        @Override
        public PrimitiveNodeType checkDiv(PrimitiveNodeType type) {
            switch (type) {
                case BOOL:
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
                default:
                    return NULL;
            }
        }

    },

    STRING {
        @Override
        public PrimitiveNodeType checkAdd(PrimitiveNodeType type) {
            switch (type) {
                case NULL:
                    return NULL;
                default:
                    return STRING;
            }
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
    },

    NULL {
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
    };


    @Override
    public String toString() {
        switch (this) {
            case BOOL:
                return "bool";
            case INT:
                return "int";
            case FLOAT:
                return "float";
            case STRING:
                return "string";
            default:
                return "undefined";
        }
    }
}