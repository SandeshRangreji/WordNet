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
    private void bfs(Queue<Integer> q1, Queue<Integer> q2){
        while(!q1.isEmpty() || !q2.isEmpty()){
            if(!q1.isEmpty()){
                int v = q1.poll();
                int s = distTo1[v]+distTo2[v];
                if(marked2[v]){
                    if( s < length || length==-1){
                        ancestor=v;
                        length=s;
                    }
                }
                if(distTo1[v] < length || length ==-1){
                    for(int a : copyG.adjacentVertices(v)){
                        if(!marked1[a]){
                            distTo1[a]=distTo1[v]+1;
                            marked1[a]=true;
                            stack1.push(a);
                            q1.add(a);
                        }
                    }
                }
            }
            if(!q2.isEmpty()){
                int v = q2.poll();
                int s = distTo1[v]+distTo2[v];
                if(marked1[v]){
                    if( s< length || length==-1){
                        ancestor=v;
                        length=s;
                    }
                }
                if (distTo2[v] < length || length==-1){
                    for(int a: copyG.adjacentVertices(v)){
                        if(!marked2[a]){
                            distTo2[a]=distTo2[v]+1;
                            marked2[a] = true;
                            stack2.push(a);
                            q2.add(a);
                        }
                    }
                }
            }
        }
        init();
    }
    private void init(){
        while(!stack1.isEmpty()){
            int x=stack1.pop();
            marked1[x]=false;
        }
        while(!stack2.isEmpty()){
            int x=stack2.pop();
            marked2[x]=false;
        }
    }

    public static void main(String[] args){
        //driver for unit testing
    }
}
