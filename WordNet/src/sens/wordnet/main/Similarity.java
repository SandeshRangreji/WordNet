package sens.wordnet.main;

import java.util.Scanner;

public class Similarity {
    private WordNet mywordNet;

    public Similarity(WordNet wordNet) {
        this.mywordNet = wordNet;
    }

    public double similarScore(String nounA, String nounB) {
        // if(nounA.equalsIgnoreCase(nounB)) return 1.0;
        String ancestorList = mywordNet.shortestAncestralPath(nounA, nounB);
        WordNet wordNet1 = new WordNet();
        String ancestors[] = ancestorList.split(" ");
        int ancestralDepth = wordNet1.distance(ancestors[0], "entity");
        WordNet wordNet2 = new WordNet();
        int depth1 = wordNet2.distance(nounA, "entity");
        WordNet wordNet3 = new WordNet();
        int depth2 = wordNet3.distance(nounB, "entity");
        System.out.println("Depth of " + nounA + " is: " + depth1 + "\nDepth of " + nounB + " is: " + depth2 + "\nAncestralDepth is: " + ancestralDepth);
        double score = (2*ancestralDepth)/(depth1+depth2+0.0000);
        return score;
    }

    public static void main(String[] args) {
        // wordnet object
        WordNet wordNet = new WordNet();
        Scanner In = new Scanner(System.in);
        Similarity similarity = new Similarity(wordNet);

        // input string array
        String A, B;
        System.out.print("Enter Noun 1 : ");
        A = In.next();
        System.out.print("Enter Noun 2 : ");
        B = In.next();
        In.close();
        // printing odd one out
        System.out.println("Similarity Score among the given nouns is: " + similarity.similarScore(A, B));
    }
}
