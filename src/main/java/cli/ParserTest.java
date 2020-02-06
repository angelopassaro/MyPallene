package cli;

import core.Lexer;
import core.Parser;
import core.ParserSym;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import lexical.ArrayStringTable;
import lexical.StringTable;

import java.io.File;
import java.io.FileInputStream;

public class ParserTest {

    private static Lexer lexer;
    private static Parser parser;
    private static ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();
    private static final String path = "/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es5_scg/testfile/input3.mypl";
    private static final StringTable stringTable = new ArrayStringTable();

    public static void main(String[] args) throws Exception {

        lexer = new Lexer(complexSymbolFactory, new FileInputStream(new File(path)), stringTable);
        parser = new Parser(lexer, complexSymbolFactory);


        //lexer OK

        Symbol token;
        try {
            while ((token = lexer.next_token()) != null) {
                if (token.sym == ParserSym.EOF) {
                    break;
                }
                String toRet = "<" +
                        ParserSym.terminalNames[token.sym] +
                        (token.value == null ? ">" : (", " + token.value + ">"));
                System.out.println(toRet);
            }
        } catch (Exception e) {
            System.out.println("File parsing ended!!");
        }

        System.out.println(parser.parse().value);
    }


}
