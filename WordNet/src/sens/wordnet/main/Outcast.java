package sens.wordnet.main;

import java.util.Scanner;

public class Outcast {
    private WordNet mywordNet;
    //Constructor with wordnet object
    public Outcast(WordNet wordNet){
        this.mywordNet = wordNet;
    }
    //Outcast method to perform outcasting
    //parameters:
    //nouns is input string array of words
    //len is length of input string array nouns
    //max is the largest distance of a word from rest of the words
    //maxpos is the position of the odd word in the array
    public String outcast(String[] nouns){
        int len = nouns.length;
        int max = 0, maxpos = 0;
        //loop that finds distance of each word from rest of the words
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
    //code to test
    public static void main(String[] args) {
        //wordnet object
        WordNet wordNet = new WordNet();
        Scanner sc=new Scanner(System.in);
        //input string array
        String nouns[] = new String[20];
        Outcast outcast = new Outcast(wordNet);
        System.out.println("Enter number of NOUNS:");
        //number of nouns
        int n=sc.nextInt();
        System.out.println("Enter Nouns:");
        //loop to input each noun
        for (int i=0;i<=n;i++){
            nouns[i]=sc.nextLine();
        }
        sc.close();
        //printing odd one out
        System.out.println(outcast.outcast(nouns));
    }
}
