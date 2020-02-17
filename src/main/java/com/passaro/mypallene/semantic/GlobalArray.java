package com.passaro.mypallene.semantic;

import java.util.ArrayList;

public class GlobalArray {

    private ArrayList<String> globalDecl = new ArrayList<>();


    public String getGlobals() {
        StringBuilder globals = new StringBuilder();
        while (this.globalDecl.size() != 0) {
            globals.append(this.globalDecl.remove(0));
        }
        return globals.toString();

    }

    public void addGlobal(String global) {
        this.globalDecl.add(global);
    }

}
