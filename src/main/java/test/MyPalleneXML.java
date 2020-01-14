package test;

import core.Parser;
import org.w3c.dom.Document;
import syntax.Program;
import template.XMLTemplate;
import visitor.ConcreteXMLVisitor;

public class MyPalleneXML {

    static Lexer.Lexer lexer;
    static Parser parser;

    public static void main(String[] args) throws Exception {

        lexer = new Lexer.Lexer();

        if (lexer.initialize(args[0])) {
            parser = new Parser(lexer);

            Program program = (Program) parser.parse().value;

            XMLTemplate xmlTemplate = new XMLTemplate();

            Document xmlDocument = xmlTemplate.create().get();

            ConcreteXMLVisitor xmlVisitor = new ConcreteXMLVisitor();

            program.accept(xmlVisitor, xmlDocument);

            xmlTemplate.write("/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es4_mpp/output/" + args[0].substring(args[0].lastIndexOf('/') + 1) + ".xml", xmlDocument);

        } else {
            System.out.println("File not found!");
        }
    }
}
