# BNPsCleave
Mass and cleave prediction of protein influenced by BNPS-skatole.
Search possible protein modifications and calculate their masses.
How to use from console (compiled on WIN):
    --modif: flag to calculate possible protein modifications (if you need). Creates new file in directory automatically.
    -I <path to fasta file>.
    -O <path to output text file>.
Example:
java -classpath .\bin ru.edu.TrpCleavage.BNPsCleave --modif -I <path to fasta file> -O <path to output text file>
