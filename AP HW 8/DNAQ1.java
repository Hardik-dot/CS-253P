import java.util.*;
public class DNAQ1 {
    public static String[] takeInput(Scanner sc){
        String[] input = new String[2];
        System.out.println("Enter DNA strand 1");
        input[0]=sc.nextLine();
        System.out.println("Enter DNA strand 2");
        input[1]=sc.nextLine();
        return input;
    }

    public static void init(int[][] strandDP, HashMap<Character,Character> validPair){
        validPair.put('A','T');validPair.put('T','A');
        validPair.put('G','C');validPair.put('C','G');
        for(int i=0;i<strandDP.length;i++)
            strandDP[i][0]=i;
        for(int i=0;i<strandDP[0].length;i++)
            strandDP[0][i]=i;
    }

    public static void sequenceAligning(int[][] strandDP, String strand1, String strand2){
        HashMap<Character,Character> validPair=new HashMap<>();
        init(strandDP,validPair);
        for(int i=1;i<strandDP.length;i++){
            for(int j=1;j<strandDP[0].length;j++){
                if(strand2.charAt(j-1)==validPair.get(strand1.charAt(i-1)))
                    strandDP[i][j]=strandDP[i-1][j-1];
                else strandDP[i][j]=Math.min(strandDP[i-1][j],strandDP[i][j-1])+1;
            }
        }
    }

    public static void addCharacters(StringBuilder dna1, StringBuilder dna2, char d1, char d2, String strand1, String strand2){
        dna1.insert(0,d1);
        dna2.insert(0,d2);
    }

    public static String[] getModifiedStrands(int[][] strandDP, String strand1, String strand2){
        int i=strand1.length(),j=strand2.length();
        StringBuilder dna1 = new StringBuilder();
        StringBuilder dna2 = new StringBuilder();
        while(i>0&&j>0) {
            if(strandDP[i][j]==strandDP[i-1][j-1]&&strandDP[i-1][j-1]<=strandDP[i-1][j]&&strandDP[i-1][j-1]<=strandDP[i][j-1]){
                addCharacters(dna1, dna2, strand1.charAt(i-1), strand1.charAt(j-1), strand1, strand2);
                i--;j--;
            }else if(strandDP[i-1][j]<=strandDP[i][j-1]){
                addCharacters(dna1, dna2, strand1.charAt(i-1), '_', strand1, strand2);
                i--;
            }else{
                addCharacters(dna1, dna2, '_', strand1.charAt(j-1), strand1, strand2);
                j--;
            }
        }

        while(i>0){
            addCharacters(dna1, dna2, strand1.charAt(i-1), '_', strand1, strand2);
            i--;
        }

        while (j>0){
            addCharacters(dna1, dna2, '_', strand1.charAt(j-1), strand1, strand2);
            j--;
        }

        return new String[]{dna1.toString(),dna2.toString()};
    }

    public static void printDNA(String[] modifiedStrands){
        System.out.println("Aligned Strands are:");
        System.out.println(modifiedStrands[0]);
        System.out.println(modifiedStrands[1]);
    }

    public static void main(String[] args) {
        String[] inputStrands = takeInput(new Scanner(System.in));
        int[][] strandDP = new int[inputStrands[0].length()+1][inputStrands[1].length()+1];
        sequenceAligning(strandDP,inputStrands[0],inputStrands[1]);
        String[] modifiedStrands = getModifiedStrands(strandDP,inputStrands[0],inputStrands[1]);
        printDNA(modifiedStrands);
    }
}
