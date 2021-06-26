package sens.wordnet.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    public int readSynsets(String synsets) {
        if (synsets == null)
            throw new IllegalArgumentException("Argument is null");
        File file = new File("synsets.txt");
        Scanner in= new Scanner(file);
        int count = 0;
        while (in.hasNextLine()) {
            count++;
            String combined = in.readLine();
            String separated[]= combined.split(",");        //segregates id and sentence in synset.txt
            int id = Integer.parseInt(separated[0]);
            ID2Noun.put(id, separated[1]);                  //pushes into hashmap id and sentence
            String words[] = separated[1].split(" ");       //segregates into words
            for (String n : words) {
                if (noun2ID.get(n) != null) {
                    Bag<Integer> bag = noun2ID.get(n);
                    bag.add(id);
                } else {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    noun2ID.put(n, bag);
                }
            }
        }
        in.close();
        return count;
    }


}
