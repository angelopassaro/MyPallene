package error;

import syntax.AstNode;

public interface ErrorHandler {

    static final String NOT_DEFINED = "Variable not defined";
    static final String YET_DEFINED = "Variable yet defined";

    void reportError(String msg, AstNode node);

    void logErrors();

    default void reportNotDefined(AstNode node) {
        this.reportError(NOT_DEFINED, node);
    }

    default void reportYetDefined(AstNode node) {
        this.reportError(YET_DEFINED, node);
    }

    boolean haveErrors();
}
