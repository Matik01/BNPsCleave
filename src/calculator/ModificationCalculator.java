package calculator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModificationCalculator implements MassCalculator {
    private HashMap<char[], Double> cleavedMass = new HashMap<>();
    private Path outputFilePath;

    private ArrayList<String> modificationLog = new ArrayList<>();
    private String protHead;

    private final HashMap<Character, Double> modificationMass = new HashMap<>() {{
        put('W', 14.0); //formation of the oxolactone on C-terminal (cleavage site)
        put('Y', 79.0); //tyrosine bromination (no phenol added)

    }};
    private Path modificationSearchFile;

    public ModificationCalculator(HashMap<char[], Double> cleavedMass, Path outputFilePath, String protHead) {
        this.cleavedMass = cleavedMass;
        this.outputFilePath = outputFilePath.toAbsolutePath();
        this.protHead = protHead;
        this.modificationSearchFile = createFile();
    }


    private void saveToFilePath(Path outputFilePath) {
        try (FileWriter fileWriter = new FileWriter(String.valueOf(outputFilePath), true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String s : modificationLog) {
                bufferedWriter.write(s + "\n");
            }
            bufferedWriter.write("\n\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void calcMass() {
        int oligoNumber = 0;
        for (Map.Entry<char[], Double> entry : cleavedMass.entrySet()) {
            char[] protein = entry.getKey();
            Double proteinMass = entry.getValue();
            oligoNumber++;
            modificationSearch(protein, proteinMass, oligoNumber);

            saveToFilePath(modificationSearchFile);

            modificationLog.clear();
        }
    }

    private void modificationSearch(char[] protein, Double mass, int oligoNumber) {
        modificationLog.add(">" + oligoNumber + ":");
        for (int i = 0; i < protein.length; i++) {
            if (protein[i] == 'Y') {
                Double subMass = mass;
                modificationLog.add(String.valueOf(i));
                modificationLog.add("Tyrosine bromination 1 time" + " + " + (subMass + modificationMass.get(protein[i])));
                modificationLog.add("Tyrosine bromination 2 times" + " + " + (subMass + modificationMass.get(protein[i]) * 2));
                modificationLog.add("Tyrosine bromination 3 times" + " + " + (subMass + modificationMass.get(protein[i]) * 3));
                modificationLog.add("Tyrosine bromination 4 times" + " + " + (subMass + modificationMass.get(protein[i]) * 4));
            } else if (protein[i] == 'W' && i == protein.length - 1) {
                Double subMass = mass;
                modificationLog.add(String.valueOf(i));
                modificationLog.add("Oxolactone on C-terminal (cleavage site)" + (subMass + modificationMass.get(protein[i])));
            } else if (protein[i] == 'W' && i != protein.length - 1) {
                Double subMass = mass;
                modificationLog.add(String.valueOf(i));
                modificationLog.add("Oxidation of Tryptophan (not cleavage site)" + (subMass + modificationMass.get(protein[i]) + 2.0));
            }
        }
    }

    private Path createFile() {
        try {
            Path newFilePath = Paths.get(outputFilePath.getParent().toString(), "ModificationSearchFile_" + outputFilePath.getFileName() + ".txt");

            if (Files.notExists(newFilePath)) {
                System.out.println(outputFilePath.getParent().toString());
                Files.createFile(newFilePath);
                System.out.println("Modification search file created in: " + newFilePath);
                return newFilePath;
            } else {
                clearFile(newFilePath);
                return newFilePath;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFile(Path fileToClear){
        try {
            new FileWriter(String.valueOf(fileToClear),false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}