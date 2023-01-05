# BNPsCleave<br />
Mass and cleave prediction of protein influenced by BNPS-skatole.<br />
Search possible protein modifications and calculate their masses.<br />
<br />
How to use BNPsCleave from console (compiled on WIN):<br />
&emsp;--modif: flag to calculate possible protein modifications (if you need). Creates new file in directory automatically.<br />
&emsp;-I [path to fasta file].<br />
&emsp;-O [path to output text file].<br />
Example:<br />
java -classpath .\bin ru.edu.TrpCleavage.BNPsCleave --modif -I [path to fasta file] -O [path to output text file]<br />
