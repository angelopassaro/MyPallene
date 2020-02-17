package com.passaro.mypallene.cli;

import com.passaro.mypallene.core.Lexer;
import com.passaro.mypallene.core.Parser;
import com.passaro.mypallene.error.ErrorHandler;
import com.passaro.mypallene.error.StackErrorHandler;
import com.passaro.mypallene.lexical.ArrayStringTable;
import com.passaro.mypallene.lexical.StringTable;
import com.passaro.mypallene.semantic.GlobalArray;
import com.passaro.mypallene.semantic.StackSymbolTable;
import com.passaro.mypallene.syntax.Program;
import com.passaro.mypallene.template.CTemplate;
import com.passaro.mypallene.template.XMLTemplate;
import com.passaro.mypallene.visitor.*;
import java_cup.runtime.ComplexSymbolFactory;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MyPallene {

    private static Lexer lexer;
    private static Parser parser;
    private static ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();

    private static final ErrorHandler errorHandler = new StackErrorHandler();
    private static final StringTable stringTable = new ArrayStringTable();
    private static final StackSymbolTable symbolTable = new StackSymbolTable(stringTable);
    private String fileName;
    private ArrayList<String> options;


    public MyPallene(String filePath, ArrayList<String> options) {
        this.fileName = filePath;
        this.options = options;
    }

    public MyPallene(String filePath) {
        this.fileName = filePath;
        this.options = new ArrayList<>();
    }


    public void compile() throws Exception {
        String output = System.getProperty("user.dir") + "/";
        String filePath = output + this.fileName.substring(this.fileName.lastIndexOf('/') + 1);


        System.out.println("Inizio compilazione");
        lexer = new Lexer(complexSymbolFactory, new FileInputStream(new File(this.fileName)), stringTable);
        parser = new Parser(lexer, complexSymbolFactory);

        Program program = (Program) parser.parse().value;

        PreScopeCheckerVisitor preScopeCheckerVisitor = new PreScopeCheckerVisitor(errorHandler);
        ScopeCheckerVisitor scopeCheckerVisitor = new ScopeCheckerVisitor(errorHandler);
        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(errorHandler);

        program.accept(preScopeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        if (errorHandler.haveErrors()) {
            errorHandler.logErrors();
            throw new Exception("Errore durante la fase di Pre Scope Checking");
        }

        program.accept(scopeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        if (errorHandler.haveErrors()) {
            errorHandler.logErrors();
            throw new Exception("Errore durante la fase di Scope Checking");
        }

        program.accept(typeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();


        if (errorHandler.haveErrors()) {
            errorHandler.logErrors();
            throw new Exception("Errore durante la fase di  Type Checking");
        }


        CTemplate template = new CTemplate();
        String model = template.create().get();

        GlobalArray globalArray = new GlobalArray();
        PreCLangVisitor PreCLangVisitor = new PreCLangVisitor(model, globalArray);
        model = program.accept(PreCLangVisitor, symbolTable);
        symbolTable.resetLevel();
        template.write(filePath, model);

        CLangVisitor cLangVisitor = new CLangVisitor(model, globalArray);
        model = program.accept(cLangVisitor, symbolTable);
        symbolTable.resetLevel();
        template.write(filePath, model);

        System.out.println("Fine compilazione");


        if (!options.isEmpty() && options.contains("xml")) {
            System.out.println("Generazione XML");
            XMLTemplate xmlTemplate = new XMLTemplate();
            Document xmlDocument = xmlTemplate.create().get();
            ConcreteXMLVisitor xmlVisitor = new ConcreteXMLVisitor();
            program.accept(xmlVisitor, xmlDocument);
            xmlTemplate.write(filePath.replace("mypl", "xml"), xmlDocument);
            System.out.println("XML generato");
        }
    }
}
