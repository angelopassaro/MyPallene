package com.passaro.mypallene.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class MyPallene2C {

    public static void main(String[] args) throws Exception {

        HashMap<Integer, String> files = generateFile();
        AtomicInteger counter = new AtomicInteger(0);
        ArrayList<String> options = new ArrayList<>();
        MyPallene compiler;
        Scanner scanner = new Scanner(System.in);

        // For format and executing with clang
        String output = System.getProperty("user.dir") + "/";
        String fileName = "";


        if (args.length == 0) {
            // options.add("xml");
            options.add("emcc");
            System.out.println("Scegli un programma:");
            files.entrySet().forEach(e -> {
                System.out.println(counter.getAndIncrement() + ") " + e.toString().substring(e.toString().lastIndexOf("/") + 1).replace(".mypl", ""));
            });
            String path = files.get(scanner.nextInt());
            fileName = output + path.substring(path.lastIndexOf('/') + 1);
            compiler = new MyPallene(path, options);
        } else {
            // options.add("xml");
            // options.add("emcc");
            fileName = output + args[0].substring(args[0].lastIndexOf('/') + 1);
            compiler = new MyPallene(args[0], options);
        }
        compiler.compile();

        System.out.println("Clang-format");
        Runtime.getRuntime().exec(String.format("clang-format -style=LLVM -i %s ", fileName.replace("mypl", "c")));
        Process p = Runtime.getRuntime().exec(String.format("clang  -pthread -lm -std=c99 %s -o %s", fileName.replace("mypl", "c"), output + "/output.out"));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        handleIO(stdInput, true);
        handleIO(stdError, false);


        if (!options.isEmpty() && options.contains("emcc")) {
            System.out.println("Emscripten");
            Process p2 = Runtime.getRuntime().exec(String.format("emcc %s -o %s", fileName.replace("mypl", "c"), fileName.replace("mypl", "html")));
            BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            BufferedReader stdError2 = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
            handleIO(stdInput2, true);
            handleIO(stdError2, false);

            System.out.println("Start server for web script");
            System.out.println(String.format("Link for web script http://localhost:8000/%s", fileName.substring(fileName.lastIndexOf("/") + 1).replace("mypl", "html")));
            Process p3 = Runtime.getRuntime().exec(String.format("python3 -m http.server --directory %s", output));
            BufferedReader stdInput3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
            BufferedReader stdError3 = new BufferedReader(new InputStreamReader(p3.getErrorStream()));
            handleIO(stdInput3, true);
            handleIO(stdError3, false);
        }

        /*
        System.out.println("Execute outuput in " + output);
        Process p2 = Runtime.getRuntime().exec(String.format("%s", output + "output.out"));
        BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        BufferedReader stdError2 = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
        handleIO(stdInput2, true);
        handleIO(stdError2, false);
         */
    }


    public static HashMap<Integer, String> generateFile() throws Exception {
        HashMap<Integer, String> files = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        //no work with jar need stream
        String path = ClassLoader.getSystemResource("program.c").getPath();
        path = path.substring(path.indexOf("/"), path.lastIndexOf("/"));
        Files.list(Path.of(path)).filter(e -> e.toString().contains(".mypl")).forEach(e -> files.put(counter.getAndIncrement(), e.toString()));
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
}
