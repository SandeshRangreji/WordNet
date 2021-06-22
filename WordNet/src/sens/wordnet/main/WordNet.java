package sens.wordnet.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordNet {

    private final Map<String, ArrayList<Integer>> noun2ID;
    private final Map<Integer, String> ID2Noun;
    private int noVertices;

    public WordNet() {

        this.noun2ID = new HashMap<String, ArrayList<Integer>>();
        this.ID2Noun = new HashMap<Integer, String>();
        this.noVertices = 0;

    }

}
