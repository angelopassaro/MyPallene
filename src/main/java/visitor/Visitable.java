package visitor;

public interface Visitable {

    /**
     * Accept the visitor
     *
     * @param <T>     return syntax.type
     * @param <P>     syntax.type od the parameter
     * @param visitor the visitor
     * @param arg     the additional argument
     * @return the returntype defined by visitor
     */
    <T, P> T accept(Visitor<T, P> visitor, P arg);

}