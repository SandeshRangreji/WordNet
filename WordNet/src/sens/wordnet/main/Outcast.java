package sens.wordnet.main;

public class Outcast {
    private WordNet mywordNet;
    
    public Outcast(WordNet wordNet){
        this.mywordNet = wordNet;
    }

    public String outcast(String[] nouns){
        int len = nouns.length;
        int max = 0, maxpos = 0;

        for (int i=0; i<len ; i++){
            int dist = 0;
            for(int j=0; j<len ; j++){
                if(i==j) continue ;

                int l = mywordNet.distance(nouns[i], nouns[j]);
                if(l==-1) dist+=(10000000);
                else dist+=1;
            }
            if(dist>max){
                max=dist;
                maxpos=i;
            }
        }
        return nouns[maxpos];
    }
}
