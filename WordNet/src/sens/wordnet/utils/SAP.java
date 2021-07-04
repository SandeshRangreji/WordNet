package sens.wordnet.utils;

import java.util.*;
public class SAP {
    private int length;               
    private int ancestor;             
    private DiGraph copyG;            
    private int[] distTo1;            
    private int[] distTo2;            
    private boolean[] marked1;        
    private boolean[] marked2;        
    private Stack<Integer> stack1;    
    private Stack<Integer> stack2;
    public SAP(DiGraph G){
        //constructor
        if(G == null){
            throw new IllegalArgumentException(" The argument to SAP is null");
        }
        copyG = new DiGraph(G);
        distTo1 = new int[G.noVertices()];
        distTo2 = new int[G.noVertices()];
        marked1 = new boolean[G.noVertices()];
        marked2 = new boolean[G.noVertices()];
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();

    }

    public int length(int v, int w){
        //length of SAP
    }

    public int ancestor(int v, int w){
        //shortest common ancestor
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){
        //length of SAP
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        //shortest common ancestor
    }

    public static void main(String[] args){
        //driver for unit testing
    }
}
