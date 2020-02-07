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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class App {

    private static Lexer lexer;
    private static Parser parser;
    private static ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();

    private static final ErrorHandler errorHandler = new StackErrorHandler();
    private static final StringTable stringTable = new ArrayStringTable();
    private static final StackSymbolTable symbolTable = new StackSymbolTable(stringTable);


    public static void compiler(String path, ArrayList<String> options) throws Exception {

        System.out.println("Inizio compilazione");
        lexer = new Lexer(complexSymbolFactory, new FileInputStream(new File(path)), stringTable);
        parser = new Parser(lexer, complexSymbolFactory);

        Program program = (Program) parser.parse().value;
        PreScopeCheckerVisitor preScopeCheckerVisitor = new PreScopeCheckerVisitor(errorHandler);
        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(errorHandler);

        ScopeCheckerVisitor scopeCheckerVisitor = new ScopeCheckerVisitor(errorHandler);
        program.accept(preScopeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        program.accept(scopeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        program.accept(typeCheckerVisitor, symbolTable);
        symbolTable.resetLevel();

        if (errorHandler.haveErrors()) {
            System.out.println("Errore durante la fase semantica");
            errorHandler.logErrors();
        }


        String output = ClassLoader.getSystemResource("output/").getPath();
        String fileName = output + path.substring(path.lastIndexOf('/') + 1);

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


        Process p = Runtime.getRuntime().exec(String.format("clang -pthread -lm -std=c99 %s -o %s", fileName.replace("mypl", "c"), output + "/output.out"));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        handleIO(stdInput, true);
        handleIO(stdError, false);

        if (!options.isEmpty() && options.contains("xml")) {
            XMLTemplate xmlTemplate = new XMLTemplate();
            Document xmlDocument = xmlTemplate.create().get();
            ConcreteXMLVisitor xmlVisitor = new ConcreteXMLVisitor();
            program.accept(xmlVisitor, xmlDocument);
            xmlTemplate.write(fileName.replace("mypl", "xml"), xmlDocument);
        }
        System.out.println("Fine compilazione");
    }


    public static HashMap<Integer, String> generateFile() throws Exception {
        HashMap<Integer, String> files = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        String path = ClassLoader.getSystemResource("input").getPath();
        Files.list(Path.of(path)).forEach(e -> files.put(counter.getAndIncrement(), e.toString()));
        return files;
    }

    private static void handleIO(BufferedReader bufferedReader, boolean out) throws IOException {
        String messages;
        while ((messages = bufferedReader.readLine()) != null) {
            if (out)
                System.out.println(messages);
            else
                System.err.println(messages);
        }
    }

    public static void main(String[] args) throws Exception {


        HashMap<Integer, String> files = generateFile();
        AtomicInteger counter = new AtomicInteger(0);
        ArrayList<String> options = new ArrayList<>();
        options.add("xml");


        System.out.println("Scegli un programma:");
        files.entrySet().forEach(e -> {
            System.out.println(counter.getAndIncrement() + ") " + e.toString().substring(e.toString().lastIndexOf("/") + 1).replace(".mypl", ""));
        });
        Scanner testNumber = new Scanner(System.in);
        String testFile = files.get(testNumber.nextInt());
        compiler(testFile, options);


    }
}
