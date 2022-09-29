package ru.edu.TrpCleavage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AminoAcidMassCalculator {
    private Path outputFilePath;
    private ArrayList<char[]> protSeq = new ArrayList<>();

    private String protHead = new String();

    private final HashMap<Character, Double> neutralProtMass = new HashMap<>() {{
        put('A', 71.03711);
        put('R', 156.10111);
        put('N', 114.04293);
        put('D', 115.02694);
        put('C', 103.00919);
        put('E', 129.04259);
        put('Q', 128.05858);
        put('G', 57.02146);
        put('H', 137.05891);
        put('I', 113.08406);
        put('L', 113.08406);
        put('K', 128.09496);
        put('M', 131.04049);
        put('F', 147.06841);
        put('P', 97.05276);
        put('S', 87.03203);
        put('T', 101.04768);
        put('W', 186.07931);
        put('Y', 163.06333);
        put('V', 99.06841);
    }};

    private HashMap<char[], Double> cleavedMass = new HashMap<>();


    public AminoAcidMassCalculator(ArrayList<ArrayList<String>> protSequence, String header) {
        for (ArrayList<String> arrayList: protSequence){
            for (String line: arrayList){
                char[] chars= line.toCharArray();
                protSeq.add(chars);
            }
        }
        this.protHead = header;
    }

    public void setOutputFilePath(String filePath){
        outputFilePath = Path.of(filePath);
        toFile();
    }

    public void calcMass() {
        for (int i = 0; i < protSeq.size(); i++) {
            Double mass = 0.0;
            char[] chars = protSeq.get(i);
            for (int j = 0; j < chars.length; j++) {
                mass += neutralProtMass.get(chars[j]);
            }
            cleavedMass.put(chars, mass);
        }
    }

    private void toFile(){
        if (Files.notExists(this.outputFilePath)){
            try {
                Files.createFile(this.outputFilePath);
                System.out.println("File created!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(outputFilePath.toAbsolutePath())){
            bufferedWriter.write(protHead + "\n");
            for (Map.Entry<char[], Double> entry: cleavedMass.entrySet()){
                String outputLine = String.valueOf(entry.getKey()) + " Oligo mass: " + entry.getValue() + "\n";
                bufferedWriter.write(outputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
