package cli;

import core.Lexer;
import core.Parser;
import java_cup.runtime.ComplexSymbolFactory;
import org.w3c.dom.Document;
import syntax.Program;
import template.XMLTemplate;
import visitor.ConcreteXMLVisitor;

import java.io.File;
import java.io.FileInputStream;

class MyPalleneXML {

    static Lexer lexer;
    static Parser parser;
    private static ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();

    public static void main(String[] args) throws Exception {

        lexer = new Lexer(complexSymbolFactory, new FileInputStream(new File(args[0])));
        parser = new Parser(lexer, complexSymbolFactory);

        Program program = (Program) parser.parse().value;

        XMLTemplate xmlTemplate = new XMLTemplate();

        Document xmlDocument = xmlTemplate.create().get();

        ConcreteXMLVisitor xmlVisitor = new ConcreteXMLVisitor();

        program.accept(xmlVisitor, xmlDocument);

        xmlTemplate.write("/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es4_mpp/output/" + args[0].substring(args[0].lastIndexOf('/') + 1) + ".xml", xmlDocument);


    }
}
