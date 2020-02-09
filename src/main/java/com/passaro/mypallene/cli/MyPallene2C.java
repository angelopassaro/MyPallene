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

/**
 * TODO Add emscripten
 * TODO Add programs
 * TODO getinput
 */

class MyPallene2C {

    public static void main(String[] args) throws Exception {

        HashMap<Integer, String> files = generateFile();
        AtomicInteger counter = new AtomicInteger(0);
        ArrayList<String> options = new ArrayList<>();
        MyPallene compiler;

        // For format and executing with clang
        String output = System.getProperty("user.dir") + "/";
        String fileName = "";


        if (args.length == 0) {
            // options.add("xml");
            System.out.println("Scegli un programma:");
            files.entrySet().forEach(e -> {
                System.out.println(counter.getAndIncrement() + ") " + e.toString().substring(e.toString().lastIndexOf("/") + 1).replace(".mypl", ""));
            });
            Scanner testNumber = new Scanner(System.in);
            String path = files.get(testNumber.nextInt());
            fileName = output + path.substring(path.lastIndexOf('/') + 1);
            compiler = new MyPallene(path, options);
        } else {
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

        System.out.println("Execute outuput in " + output);
        Process p2 = Runtime.getRuntime().exec(String.format("%s", output + "output.out"));
        BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        BufferedReader stdError2 = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
        handleIO(stdInput2, true);
        handleIO(stdError2, false);
    }


    public static HashMap<Integer, String> generateFile() throws Exception {
        HashMap<Integer, String> files = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        //no work with jar need stream
        String path = ClassLoader.getSystemResource("input1.mypl").getPath();
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
