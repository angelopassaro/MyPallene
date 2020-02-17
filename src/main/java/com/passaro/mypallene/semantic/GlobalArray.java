package com.passaro.mypallene.semantic;

import java.util.Stack;

public class GlobalArray {

    private Stack<String> listArray = new Stack<>();

    public String getGlobals() {
        StringBuilder globals = new StringBuilder();
        while (this.listArray.size() != 0) {
            globals.append(this.listArray.pop());
        }
        return globals.toString();

    }

    public void addGlobal(String global) {
        this.listArray.push(global);
    }
}
