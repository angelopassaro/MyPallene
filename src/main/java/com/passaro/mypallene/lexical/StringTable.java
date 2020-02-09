package com.passaro.mypallene.lexical;

public interface StringTable {

    /**
     * A string table is used to memorize the identifier encountered during com.passaro.mypallene.lexical
     * analysis
     */


    /**
     * Add a new com.passaro.mypallene.lexical symbol to table. I f the symbol is yet present it isn't added to table
     *
     * @param lexeme the symbol name
     * @return true if no duplicates are found, false otherwise
     */
    boolean install(String lexeme);

    /**
     * Get the addres for a specific lexeme
     *
     * @param lexeme the lexeme for searching
     * @return the address of the lexeme
     */
    int getAddress(String lexeme);

    /**
     * Get the lexeme associated with this address
     *
     * @param address the address to search
     * @return the lexeme associated
     */
    String getLexeme(int address);

}
