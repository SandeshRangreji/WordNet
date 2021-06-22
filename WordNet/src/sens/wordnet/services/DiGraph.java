package sens.wordnet.services;

import java.util.Scanner;

public class DiGraph {

    private final int vertices;
    private int edges;
    private Bag<Integer>[] adj;
    private int[] indegree;
    
    public DiGraph(int vertices) {
        this.vertices = vertices;
        this.edges = 0;
        this.indegree = new int[this.vertices];
        this.adj = (Bag<Integer>[]) new Bag[this.vertices];
        for (int i=0; i<this.vertices; i++) {
            this.adj[i] = new Bag<Integer>();
        }
    }

    public int noVertices() {
        return this.vertices;
    }

    public int noEdges() {
        return this.edges;
    }

    public void addEdge(int vertex1, int vertex2) {
        this.adj[vertex1].add(vertex2);
        this.indegree[vertex2]++;
        this.edges++;
    }

    public Iterable<Integer> adjacentVertices(int vertex1) {
        return adj[vertex1];
    }

    public int outDegree(int vertex1) {
        return adj[vertex1].size();
    }

    public int inDegree(int vertex1) {
        return indegree[vertex1];
    }

    //Reverse The Graph
    public DiGraph reverse() {
        DiGraph rev = new DiGraph(vertices);
        for (int i=0;i<vertices;i++){
            for(int j=0;j<adj[i].size();j++){
                rev.addEdge(j, i);
            }
        }
        return rev;
    }
    public String toString() {
        String s="";
        for (int i=0;i<vertices;i++)
        {
            String st = Integer.toString(i);
            s=s+st+":";
            for(int j : adj[i])
            {
                String st1 = Integer.toString(j);
                s=s+st1+" "; 
            }
            s=s+"\n"; 
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Scanner In = new Scanner(System.in);
        System.out.print("Enter the no. of vertices: ");
        int n = In.nextInt();
        DiGraph graph = new DiGraph(n);
        for(int i=0; i<n; i++) {
            System.out.print("Enter the no. of connected vertices for vertex " + i +": ");
            int noOut = In.nextInt();
            for(int j=0; j<noOut; j++) {
                System.out.print("Enter connected vertex: ");
                int v = In.nextInt();
                graph.addEdge(i, v);
            }
        }
        System.out.println(graph.toString());
        System.out.println("Number of Edges: " + graph.noEdges());
        System.out.println("Number of Vertices: " + graph.noVertices());
        System.out.println("Number of InDegree for 1: " + graph.inDegree(1));
        System.out.println("Number of OutDegree for 2: " + graph.outDegree(2));

        DiGraph reverse=graph.reverse();

        System.out.println("Number of Edges: " + reverse.noEdges());
        System.out.println("Number of Vertices: " + reverse.noVertices());
        System.out.println("Number of InDegree for 1: " + reverse.inDegree(1));
        System.out.println("Number of InDegree for 3: " + reverse.inDegree(3));
        In.close();
    }

}
