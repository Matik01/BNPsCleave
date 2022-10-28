package ru.edu.TrpCleavage;

import java.util.Arrays;
public class BNPsCleave {
    private static boolean showUsage = true;
    public static String[] usageArgs = null;

    public static void main(String[] args) {
        if (args.length > 0) {
            usageArgs = Arrays.copyOfRange(args, 0, args.length);
            if (usageArgs[0].equals("-I") && usageArgs[2].equals("-O")) {
                ProteinIterator.cleavage();
                AminoAcidMassCalculator calculator = new AminoAcidMassCalculator(ProteinIterator.cleavedSeq, ProteinIterator.proteinHeader);
                calculator.calcMass();
                calculator.setOutputFilePath(usageArgs[3]);
            } else {
                showUsage = false;
            }
        } else {
            showUsage = false;
        }
        if (!showUsage) {
            System.err.println("Usage: ");
            System.err.print("[-I <path to fasta file>] [-O <path to output text file>].");
        }
    }


}
