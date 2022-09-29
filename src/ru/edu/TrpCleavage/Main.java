package ru.edu.TrpCleavage;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        ProteinIterator.cleavage();
        AminoAcidMassCalculator calculator = new AminoAcidMassCalculator(ProteinIterator.cleavedSeq, ProteinIterator.proteinHeader);
        calculator.calcMass();
        calculator.setOutputFilePath(console.nextLine());
    }
}
