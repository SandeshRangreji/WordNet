package sens.wordnet.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import sens.wordnet.utils.*;

public class WordNet {

    // Map that returns ID of a given noun
    private final Map<String, ArrayList<Integer>> noun2ID;
    // Map that returns noun of the given ID
    private final Map<Integer, String> ID2Noun;
    // Number of vertices of WordNet
    private int noVertices;
    // Shortest Ancestral Path object
    private SAP sap;

    // Constructor
    public WordNet() {

        // initializing data members
        this.noun2ID = new HashMap<String, ArrayList<Integer>>();
        this.ID2Noun = new HashMap<Integer, String>();
        this.noVertices = 0;
        // creating the WordNet digraph
        this.readSynsets();
        this.readHypernyms();

    }

    public Iterable<String> nouns() {
        return noun2ID.keySet(); // returns all key elements of noun2ID
    }

    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("Input is null.");
        return noun2ID.containsKey(word); // returns if the word is present in noun2ID
    }

    // function to retrieve the shortest ancestor of two given nouns
    public String shortestAncestralPath(String nounX, String nounY) {
        // Parameters :
        // nounX : a given noun
        // nounY : a given noun
        // returns:
        // ------------to be filled---------------------

        // checks if the given inputs are valid nouns
        if (!isNoun(nounX) || !isNoun(nounY)) {
            throw new IllegalArgumentException("Input is not a given WordNet noun.");
        }

        // returns the shortest common ancestor to the two given nouns
        return ID2Noun.get(this.sap.ancestor(noun2ID.get(nounX), noun2ID.get(nounY)));
    }

    // function to find the distance between two given nouns
    public int distance(String nounX, String nounY) {
        // Parameters :
        // nounX : a given noun
        // nounY : noun to calculate distance from nounX
        // returns:
        // ------------to be filled---------------------

        // checks if the given inputs are valid nouns
        if (!isNoun(nounX) || !isNoun(nounY)) {
            throw new IllegalArgumentException("Input is not a given WordNet noun.");
        }

        // returns distance between the nouns
        return this.sap.length(noun2ID.get(nounX), noun2ID.get(nounY));
    }

    public String readLine(Scanner in) {
        String line;
        try {
            line = in.nextLine();
        } catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    private void readSynsets() {
        File file = new File("./src/sens/wordnet/assets/synsets.txt");
        Scanner in;
        try {
            in = new Scanner(file);
            while (in.hasNextLine()) {
                String combined = readLine(in);
                String[] separated = combined.split(","); // segregates id and sentence in synset.txt
                if (separated.length < 2) {
                    continue;
                }
                ++noVertices;
                int id = Integer.parseInt(separated[0]);
                ID2Noun.put(id, separated[1]); // pushes into hashmap id and sentence
                String words[] = separated[1].split(" "); // segregates into words
                for (String word : words) {
                    ArrayList<Integer> ids = noun2ID.get(word);
                    if (ids != null) {
                        ids.add(id);
                    } else {
                        ArrayList<Integer> nids = new ArrayList<Integer>();
                        nids.add(id);
                        noun2ID.put(word, nids);
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // function to read Hypernyms and create a Digraph out of the data
    private void readHypernyms() {
        // reads file
        File file = new File("./src/sens/wordnet/assets/hypernyms.txt");
        Scanner in;
        String line;
        // initialise digraph of words
        DiGraph digraph = new DiGraph(this.noVertices);
        try {
            // read line by line
            in = new Scanner(file);
            while (in.hasNextLine()) {
                // reads the given line
                line = readLine(in);
                // split the data into id and adjacent nodes
                String[] seperatedStrings = line.split(",");
                // no adjacent nodes
                if (seperatedStrings.length < 2) {
                    continue;
                }
                // sets current node to the first integer read
                int currentNode = Integer.parseInt(seperatedStrings[0]);
                for (int i = 1; i < seperatedStrings.length; ++i) {
                    // sets adjacent nodes to every subsequent integer read
                    digraph.addEdge(currentNode, Integer.parseInt(seperatedStrings[i]));
                }
            }
            // create a directed cycle object out of the digraph to detect cycles
            DirectedCycle dc = new DirectedCycle(digraph);
            if (dc.hasCycle()) {
                throw new IllegalArgumentException("Cycle detected");
            }
            // find number of roots of the digraph, there should only be one
            int noRoot = 0;
            for (int i = 0; i < digraph.noVertices(); ++i) {
                if (digraph.outDegree(i) == 0) {
                    ++noRoot;
                    if (noRoot > 1) {
                        throw new IllegalArgumentException("More than 1 root");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // create SAP object out of the digraph
        this.sap = new SAP(digraph);
    }

    public static void main(String[] args) {
        // driver for unit testing

        Scanner In = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String word1 = In.next();
        System.out.print("Enter another word: ");
        String word2 = In.next();
        // create WordNet object
        WordNet wordNet = new WordNet();
        int shortestDistance = wordNet.distance(word1, word2);
        String commonAncestor = wordNet.shortestAncestralPath(word1, word2);
        System.out.println(commonAncestor + " is the common ancestor of the words " + word1 + " and " + word2
                + ", and are at a distance of " + shortestDistance + " from each other.");
    }

}
