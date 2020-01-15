package lexical;

public interface StringTable {

    /**
     * A string table is used to memorize the identifier encountered during lexical
     * analysis
     */

    boolean install(String lexeme);

    int getAddress(String lexeme);

    String getLexeme(int address);

}
