package sens.wordnet.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import sens.wordnet.services.DiGraph;
import sens.wordnet.services.DirectedCycle;

public class WordNet {

    private final Map<String, ArrayList<Integer>> noun2ID;
    private final Map<Integer, String> ID2Noun;
    private int noVertices;

    public WordNet() {

        this.noun2ID = new HashMap<String, ArrayList<Integer>>();
        this.ID2Noun = new HashMap<Integer, String>();
        this.noVertices = 0;

    }
    public Iterable<String> nouns() {
        return noun2ID.keySet();        //returns all key elements of noun2ID
    }

    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("Input is null.");
        return noun2ID.containsKey(word);           //returns if the word is present in noun2ID 
    }

    public String readLine(Scanner in) {
        String line;
        try {
            line = in.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    private void readSynsets() {
        File file = new File("D:/WordNet/src/sens/wordnet/assets/synsets.txt");
        Scanner in;
        try {
            in = new Scanner(file);
            while (in.hasNextLine()) {
                String combined = readLine(in);
                String[] separated= combined.split(",");        //segregates id and sentence in synset.txt
                if (separated.length < 2) {
                    continue;
                }
                ++noVertices;
                int id = Integer.parseInt(separated[0]);
                ID2Noun.put(id, separated[1]);                  //pushes into hashmap id and sentence
                String words[] = separated[1].split(" ");       //segregates into words
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

    private void readHypernyms() {
        File file = new File("D:/WordNet/src/sens/wordnet/assets/hypernyms.txt");
        Scanner in;
        String line;
        DiGraph digraph = new DiGraph(this.noVertices);
        try {
            in = new Scanner(file);
            while (in.hasNextLine()) {
                line = readLine(in);
                String[] seperatedStrings = line.split(",");
                if (seperatedStrings.length < 2) {
                    continue;
                }
                int start = Integer.parseInt(seperatedStrings[0]);
                for (int i = 1; i < seperatedStrings.length; ++i) {
                    digraph.addEdge(start, Integer.parseInt(seperatedStrings[i]));
                }
            }
            DirectedCycle dc = new DirectedCycle(digraph);
            if (dc.hasCycle()) {
                throw new IllegalArgumentException("Cycle detected");
            }
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
    }

}
