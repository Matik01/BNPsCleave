package ru.edu.TrpCleavage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProteinIterator {
    private static Path inputPath;

    public static ArrayList<ArrayList<String>> cleavedSeq = new ArrayList<>();

    public static String proteinHeader;

    private static String fastaParser() {
        Scanner console = new Scanner(System.in);
        inputPath = Path.of(console.nextLine());
        String outputProteinLine = null;
        try (BufferedReader bufferedReader = Files.newBufferedReader(inputPath.toAbsolutePath())) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (line.startsWith(">")) {
                    proteinHeader = line;
                } else {
                    outputProteinLine = line.toUpperCase();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputProteinLine;
    }

    public static void cleavage() {
        ArrayList<String> proteinSeq = new ArrayList<>();
        String lol = fastaParser();
        for (int i = 0; i < lol.length(); i++) {
            proteinSeq.add(String.valueOf(lol.charAt(i)));
        }

        ArrayList<Integer> trpIndexes = indexation(proteinSeq);
        for (int i = 0; i < trpIndexes.size(); i++) {
            int startIndx = trpIndexes.get(i);
            protCleaver(proteinSeq, startIndx);
        }

    }

    private static ArrayList<Integer> indexation(ArrayList<String> proteinSeq) {
        ArrayList<Integer> trpIndexes = new ArrayList<>();

        for (int i = 0; i < proteinSeq.size(); i++) {
            if (i == 0) {
                trpIndexes.add(i);
            } else if (proteinSeq.get(i).equals("W")) {
                trpIndexes.add(i + 1);
            }

        }
        return trpIndexes;
    }

    private static void protCleaver(List<String> proteinSeq, int startIndx) {
        StringBuilder temp = new StringBuilder();

        for (int i = startIndx; i < proteinSeq.size(); i++) {
            temp.append(proteinSeq.get(i));
            if (proteinSeq.get(i).equals("W")) {
                ArrayList<String> outTemp = new ArrayList<>();
                outTemp.add(String.valueOf(temp));
                cleavedSeq.add(outTemp);
            } else if (i == proteinSeq.size() - 1) {
                ArrayList<String> tempFull = new ArrayList<>();
                tempFull.add(String.valueOf(temp));
                cleavedSeq.add(tempFull);
            }
        }
    }
}
