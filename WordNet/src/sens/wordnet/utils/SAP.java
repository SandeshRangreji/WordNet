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
        copyG = G.reverse();
        distTo1 = new int[G.noVertices()];
        distTo2 = new int[G.noVertices()];
        marked1 = new boolean[G.noVertices()];
        marked2 = new boolean[G.noVertices()];
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();

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

    private void compute(int v, int w) {
        length = -1;
        ancestor = -1;
        distTo1[v] = 0;
        distTo2[w] = 0;
        marked1[v] = true;
        marked2[w] = true;
        stack1.push(v);
        stack2.push(w);
        Queue<Integer> q1 = new PriorityQueue<>();
        Queue<Integer> q2 = new PriorityQueue<>();
        q1.add(v);
        q2.add(w);
        bfs(q1, q2);
    }

    private void compute(Iterable<Integer> v, Iterable<Integer> w) {
        length = -1;
        ancestor = -1;
        Queue<Integer> q1 = new PriorityQueue<>();
        Queue<Integer> q2 = new PriorityQueue<>();
        for (int v1 : v) {
            marked1[v1] = true;
            stack1.push(v1);
            distTo1[v1] = 0;
            q1.add(v1);
        }
        for (int w1 : w) {
            marked2[w1] = true;
            stack2.push(w1);
            distTo2[w1] = 0;
            q2.add(w1);
        }
        bfs(q1, q2);
    }

    public int length(int v, int w){
        //length of SAP
        compute(v, w);
        return length;
    }

    public int ancestor(int v, int w){
        //shortest common ancestor
        compute(v, w);
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){
        //length of SAP
        compute(v, w);
        return length;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        //shortest common ancestor
        compute(v, w);
        return ancestor;
    }

    public static void main(String[] args){
        //driver for unit testing
        Scanner In = new Scanner(System.in);
        System.out.print("Enter the no. of vertices: ");
        int n = In.nextInt();
        // create Di Graph object
        DiGraph graph = new DiGraph(n);
        // taking in input for creating edges to the digraph
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the no. of connected vertices for vertex " + i + ": ");
            int noOut = In.nextInt();
            for (int j = 0; j < noOut; j++) {
                System.out.print("Enter connected vertex: ");
                int v = In.nextInt();
                graph.addEdge(i, v);
            }
        }
        SAP sap = new SAP(graph);
        System.out.println("Current graph is: ");
        System.out.println(graph.toString());
        System.out.print("Enter the first vertex: ");
        int v = In.nextInt();
        System.out.print("Enter the other vertex: ");
        int w = In.nextInt();
        System.out.println("Length of shortest ancestral path is: " + sap.length(v,w));
        System.out.println("Shortest common ancestor is: " + sap.ancestor(v,w));
    }
}
