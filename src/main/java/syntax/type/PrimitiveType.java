package syntax.type;


public enum PrimitiveType implements Type {

    INT {
        @Override
        public PrimitiveType checkAdd(PrimitiveType type) {
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
        public PrimitiveType checkSub(PrimitiveType type) {
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
        public PrimitiveType checkMul(PrimitiveType type) {
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
        public PrimitiveType checkDiv(PrimitiveType type) {
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
        public PrimitiveType checkAdd(PrimitiveType type) {
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
        public PrimitiveType checkSub(PrimitiveType type) {
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
        public PrimitiveType checkMul(PrimitiveType type) {
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
        public PrimitiveType checkDiv(PrimitiveType type) {
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
        public PrimitiveType checkAdd(PrimitiveType type) {
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
        public PrimitiveType checkSub(PrimitiveType type) {
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
        public PrimitiveType checkMul(PrimitiveType type) {
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
        public PrimitiveType checkDiv(PrimitiveType type) {
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
        public PrimitiveType checkAdd(PrimitiveType type) {
            switch (type) {
                case NULL:
                    return NULL;
                default:
                    return STRING;
            }
        }

        @Override
        public PrimitiveType checkSub(PrimitiveType type) {
            return null;
        }

        @Override
        public PrimitiveType checkMul(PrimitiveType type) {
            return null;
        }

        @Override
        public PrimitiveType checkDiv(PrimitiveType type) {
            return null;
        }
    },

    NULL {
        @Override
        public PrimitiveType checkAdd(PrimitiveType type) {
            return null;
        }

        @Override
        public PrimitiveType checkSub(PrimitiveType type) {
            return null;
        }

        @Override
        public PrimitiveType checkMul(PrimitiveType type) {
            return null;
        }

        @Override
        public PrimitiveType checkDiv(PrimitiveType type) {
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