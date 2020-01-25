package cli;

import core.Lexer;
import core.Parser;
import error.ErrorHandler;
import error.StackErrorHandler;
import java_cup.runtime.ComplexSymbolFactory;
import lexical.ArrayStringTable;
import lexical.StringTable;
import org.w3c.dom.Document;
import semantic.StackSymbolTable;
import semantic.SymbolTable;
import syntax.Program;
import template.XMLTemplate;
import visitor.ConcreteXMLVisitor;
import visitor.PreScopeCheckerVisitor;
import visitor.ScopeCheckerVisitor;

import java.io.File;
import java.io.FileInputStream;

class App {

    private static Lexer lexer;
    private static Parser parser;
    private static ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();
    private static final String path = "/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es5_scg/testfile/input1";
    private static final ErrorHandler errorHandler = new StackErrorHandler();
    private static final StringTable stringTable = new ArrayStringTable();
    private static final SymbolTable symbolTable = new StackSymbolTable(stringTable);

    public static void main(String[] args) throws Exception {

        lexer = new Lexer(complexSymbolFactory, new FileInputStream(new File(path)), stringTable);
        parser = new Parser(lexer, complexSymbolFactory);

        Program program = (Program) parser.parse().value;
        PreScopeCheckerVisitor preScopeCheckerVisitor = new PreScopeCheckerVisitor(errorHandler);
        ScopeCheckerVisitor scopeCheckerVisitor = new ScopeCheckerVisitor(errorHandler);
        boolean prescope = program.accept(preScopeCheckerVisitor, symbolTable);
        boolean scope = program.accept(scopeCheckerVisitor, symbolTable);
        System.out.println("PreScope: " + prescope + " scope: " + scope);
        if (errorHandler.haveErrors()) {
            errorHandler.logErrors();
        }

        XMLTemplate xmlTemplate = new XMLTemplate();

        Document xmlDocument = xmlTemplate.create().get();
        ConcreteXMLVisitor xmlVisitor = new ConcreteXMLVisitor();
        program.accept(xmlVisitor, xmlDocument);
        xmlTemplate.write("/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es5_scg/output/" + path.substring(path.lastIndexOf('/') + 1) + ".xml", xmlDocument);
    }
}
