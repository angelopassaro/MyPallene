package com.passaro.mypallene.error;

import com.passaro.mypallene.syntax.AstNode;

import java.util.Stack;

public class StackErrorHandler implements ErrorHandler {

    private final Stack<String> catchedErrors;

    public StackErrorHandler() {
        this.catchedErrors = new Stack<>();
    }

    @Override
    public void reportError(String msg, AstNode node) {
        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(msg);
        errorBuilder.append(" at ");
        errorBuilder.append("Line: ");
        errorBuilder.append(node.getLeftLocation().getLine());
        errorBuilder.append(" Column: ");
        errorBuilder.append(node.getRightLocation().getColumn());
        this.catchedErrors.push(errorBuilder.toString());
    }

    @Override
    public void logErrors() {
        this.catchedErrors.forEach(System.out::println);
    }

    @Override
    public boolean haveErrors() {
        return !this.catchedErrors.isEmpty();
    }
}
