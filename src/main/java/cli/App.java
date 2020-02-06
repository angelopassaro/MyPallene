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
import syntax.Program;
import template.CTemplate;
import template.XMLTemplate;
import visitor.*;

import java.io.File;
import java.io.FileInputStream;

class App {

    private static Lexer lexer;
    private static Parser parser;
    private static ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();
    private static final String path = "/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es5_scg/testfile/input3.mypl";
    private static final ErrorHandler errorHandler = new StackErrorHandler();
    private static final StringTable stringTable = new ArrayStringTable();
    private static final StackSymbolTable symbolTable = new StackSymbolTable(stringTable);

    public static void main(String[] args) throws Exception {

        lexer = new Lexer(complexSymbolFactory, new FileInputStream(new File(path)), stringTable);
        parser = new Parser(lexer, complexSymbolFactory);

        Program program = (Program) parser.parse().value;
        PreScopeCheckerVisitor preScopeCheckerVisitor = new PreScopeCheckerVisitor(errorHandler);
        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(errorHandler);


        ScopeCheckerVisitor scopeCheckerVisitor = new ScopeCheckerVisitor(errorHandler);
        boolean prescope = program.accept(preScopeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        boolean scope = program.accept(scopeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        program.accept(typeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        if (errorHandler.haveErrors()) {
            System.out.println("Errore durante la fase semantica");
            errorHandler.logErrors();
        }


        // System.out.println("PreScope: " + prescope + " scope: " + scope);
        // System.out.println(symbolTable.toString());

        String fileName = "/home/angelo/Documents/Universita/compilatori(GENNAIO)/esercizi/passaro_es5_scg/output/" + path.substring(path.lastIndexOf('/') + 1);

        CTemplate template = new CTemplate();
        String model = template.create().get();

        PreCLangVisitor PreCLangVisitor = new PreCLangVisitor(model);
        model = program.accept(PreCLangVisitor, symbolTable);
        symbolTable.resetLevel();
        template.write(fileName, model);

        CLangVisitor cLangVisitor = new CLangVisitor(model);
        model = program.accept(cLangVisitor, symbolTable);
        symbolTable.resetLevel();
        template.write(fileName, model);

        if (args.length > 0 && args[0].equals("xml")) {
            XMLTemplate xmlTemplate = new XMLTemplate();
            Document xmlDocument = xmlTemplate.create().get();
            ConcreteXMLVisitor xmlVisitor = new ConcreteXMLVisitor();
            program.accept(xmlVisitor, xmlDocument);
            xmlTemplate.write(fileName.replace("mypl", "xml"), xmlDocument);
        }


    }
}
