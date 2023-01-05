package ru.edu.TrpCleavage;

import calculator.AminoAcidMassCalculator;
import calculator.ModificationCalculator;

import java.util.Arrays;

public class BNPsCleave {
    private static boolean showUsage = true;
    public static String[] USAGE_ARGS = null;
    private static boolean isModif = true;

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("--modif")) {
                USAGE_ARGS = Arrays.copyOfRange(args, 1, args.length);
                start();
            } else {
                USAGE_ARGS = Arrays.copyOfRange(args, 0, args.length);
                isModif = false;
                start();
            }

        } else {
            showUsage = false;
        }
        if (!showUsage) {
            System.err.println("Usage: ");
            System.err.println("[--modif <flag to calculate possible protein modifications.\nCreates new file in directory automatically>].");
            System.err.print("[-I <path to fasta file>] [-O <path to output text file>].");
        }
    }

    private static void start(){
        AminoAcidMassCalculator calculator;
        ProteinCleaver.cleavage();

        if (USAGE_ARGS[0].equals("-I") && USAGE_ARGS[2].equals("-O")) {
            calculator = new AminoAcidMassCalculator(ProteinCleaver.getCleavedSeq(), ProteinCleaver.getProteinHeader());
            calculator.calcMass();
            calculator.setOutputFilePath(USAGE_ARGS[3]);
            if (isModif) {
                ModificationCalculator modifCalculation = new ModificationCalculator(calculator.getCleavedMass(), calculator.getOutputFilePath(), calculator.getProtHead());
                modifCalculation.calcMass();
            }
        }  else {
            showUsage = false;
        }
    }
}
