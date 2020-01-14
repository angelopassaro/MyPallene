package test;


import core.Parser;

public class ParserTest {

    static Lexer.Lexer lexer;
    static Parser parser;

    //TODO args input
    public static void main(String[] args) throws Exception {

        lexer = new Lexer.Lexer();

        if (lexer.initialize(args[0])) {
            parser = new Parser(lexer);
            System.out.println(parser.parse().value);
        } else {
            System.out.println("File not found!");
        }
    }
}