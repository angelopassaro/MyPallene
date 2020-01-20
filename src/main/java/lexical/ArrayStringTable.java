package lexical;

import java.util.ArrayList;
import java.util.Objects;

public class ArrayStringTable implements StringTable {

    private ArrayList<String> lexicalSymbols;

    public ArrayStringTable() {
        this.lexicalSymbols = new ArrayList<String>();
    }

    @Override
    public boolean install(String lexeme) {
        if (this.getAddress(lexeme) == -1) {
            return this.lexicalSymbols.add(lexeme);
        } else {
            return false;
        }
    }

    @Override
    public int getAddress(String lexeme) {
        return this.lexicalSymbols.indexOf(lexeme);
    }

    @Override
    public String getLexeme(int address) {
        return this.lexicalSymbols.get(address);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.lexicalSymbols);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            final ArrayStringTable other = (ArrayStringTable) obj;
            return this.lexicalSymbols.equals(other.lexicalSymbols);
        }
    }

}
