package test;

import core.Lexer;
import core.ParserSym;
import java_cup.runtime.Symbol;

public class LexerTest {

    public static void main(String[] args) {

        Lexer.Lexer lexicalAnalyzer = new Lexer.Lexer();

        if (lexicalAnalyzer.initialize(args[0])) {
            Symbol token;
            try {
                while ((token = lexicalAnalyzer.next_token()) != null) {
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

        } else {
            System.out.println("File not found!!");
        }
    }
}