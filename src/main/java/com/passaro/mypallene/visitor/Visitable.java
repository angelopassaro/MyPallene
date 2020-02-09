package com.passaro.mypallene.visitor;

public interface Visitable {

    /**
     * Accept the com.passaro.mypallene.visitor
     *
     * @param <T>     return com.passaro.mypallene.syntax.type
     * @param <P>     com.passaro.mypallene.syntax.type od the parameter
     * @param visitor the com.passaro.mypallene.visitor
     * @param arg     the additional argument
     * @return the returntype defined by com.passaro.mypallene.visitor
     */
    <T, P> T accept(Visitor<T, P> visitor, P arg);

}