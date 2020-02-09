package com.passaro.mypallene.error;

import com.passaro.mypallene.nodetype.NodeType;
import com.passaro.mypallene.syntax.AstNode;

public interface ErrorHandler {

    static final String NOT_DEFINED = "Variable not defined";
    static final String YET_DEFINED = "Variable yet defined";
    static final String TYPE_MISMATCH = "Type mismatch: Expected %s but found %s";

    void reportError(String msg, AstNode node);

    void logErrors();

    default void reportNotDefined(AstNode node) {
        this.reportError(NOT_DEFINED, node);
    }

    default void reportYetDefined(AstNode node) {
        this.reportError(YET_DEFINED, node);
    }

    default void reportTypeMismatch(NodeType expected, NodeType actual, AstNode node) {
        String msg = String.format(TYPE_MISMATCH, expected, actual);
        this.reportError(msg, node);
    }


    boolean haveErrors();
}
