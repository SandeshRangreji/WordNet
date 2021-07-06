package sens.wordnet.utils;

import java.util.Scanner;
import java.util.Stack;

// class for Directed Graph
public class DiGraph {

    // number of vertices
    private final int vertices;
    // number of edges
    private int edges;
    // Bag of bags that contains the adjacent vertices to the given vertex
    private Bag<Integer>[] adj;
    // list of vertices that have a direct adge to the given vertex
    private int[] indegree;

    // Constructor
    public DiGraph(int vertices) {
        // Parameters :
        // vertices : number of vertices
        this.vertices = vertices;
        this.edges = 0;
        this.indegree = new int[this.vertices];
        this.adj = (Bag<Integer>[]) new Bag[this.vertices];
        for (int i = 0; i < this.vertices; i++) {
            this.adj[i] = new Bag<Integer>();
        }
    }

    public DiGraph(DiGraph G){
        if(G == null) throw new IllegalArgumentException("Null Argument");

        this.vertices=G.noVertices();
        this.edges = G.noEdges();
        if (noVertices() < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");

        indegree = new int[this.vertices];
        for(int v=0; v < this.vertices; v++){
            this.indegree[v] = G.inDegree(v);
        }

        this.adj = (Bag<Integer>[]) new Bag[this.vertices];
        for (int v = 0; v < this.vertices; v++) {
            this.adj[v] = new Bag<Integer>();
        }

        for (int v = 0; v < this.vertices; v++) {
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    // Member function to return number of vertices
    public int noVertices() {
        // Return :
        // number of vertices
        return this.vertices;
    }

    // Member function to return number of edges
    public int noEdges() {
        // Return :
        // number of edges
        return this.edges;
    }

    // Member function to add a directed edge between two given vertices
    public void addEdge(int vertex1, int vertex2) {
        // Parameters :
        // vertex1 : Vertex the edge is coming from
        // vertex2 : Vertex the edge is going to

        // adds the destination vertex to the Bag of adjacent vertices of the source
        // vertex
        this.adj[vertex1].add(vertex2);
        // increments the indegree of the destination vertex
        this.indegree[vertex2]++;
        // increments the number of edges
        this.edges++;
    }

    // Member function to return a Bag of adjacent vertices to the given vertex
    public Iterable<Integer> adjacentVertices(int vertex1) {
        // Parameters :
        // vertex1 : the vertex whose adjacent vertices will be returned
        // Return :
        // Bag of adjacent vertices of the given vertex
        return adj[vertex1];
    }

    // Member function to return the outDegree of the given vertex
    public int outDegree(int vertex1) {
        // Parameter :
        // vertex1 : the given vertex
        // Return :
        // Out Degree of the given vertex
        return adj[vertex1].size();
    }

    // Member function to return the In Degree of the given vertex
    public int inDegree(int vertex1) {
        // Parameter :
        // vertex1 : the given vertex
        // Return :
        // In Degree of the given vertex
        return indegree[vertex1];
    }

    // Reverse The Graph
    public DiGraph reverse() {
        // Return :
        // rev : reverse of the di graph object

        // initialise new object for the reverse Di Graph
        DiGraph rev = new DiGraph(vertices);
        // iterating through vertices and their adjacent vertices to reverse the
        // direction of the edge
        for (int i = 0; i < vertices; i++) {
            for(int j: adj[i]){
                // creating an edge in the new object that has the reverse direction of the
                // respective edge in this object
                rev.addEdge(j, i);
            }
        }
        return rev;
    }

    // toString
    public String toString() {
        // initializing an empty string
        String s = "";
        for (int i = 0; i < vertices; i++) {
            // appends vertex number to the string
            String st = Integer.toString(i);
            s = s + st + ":";
            // iterating through the adjacent vertices of the given vertex
            for (int j : adj[i]) {
                // appends adjacent vertices to the string
                String st1 = Integer.toString(j);
                s = s + st1 + " ";
            }
            // appends a new line character
            s = s + "\n";
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // input number of vertices
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
        // Printing details on the DiGraph
        System.out.println(graph.toString());
        System.out.println("Number of Edges: " + graph.noEdges());
        System.out.println("Number of Vertices: " + graph.noVertices());
        System.out.println("Number of InDegree for 1: " + graph.inDegree(1));
        System.out.println("Number of OutDegree for 2: " + graph.outDegree(2));

        // Reversing the DiGraph
        DiGraph reverse = graph.reverse();

        // Printing details on the reversed DiGraph
        System.out.println("Reversed graph is: ");
        System.out.println(reverse.toString());
        System.out.println("Number of Edges: " + reverse.noEdges());
        System.out.println("Number of Vertices: " + reverse.noVertices());
        for(int i = 0; i < reverse.noVertices(); i++){
            System.out.println("Number of InDegree for " + i + ": " + reverse.inDegree(i));
        }
        In.close();
    }
}
