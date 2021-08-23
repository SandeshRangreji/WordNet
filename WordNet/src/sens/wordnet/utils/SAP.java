package sens.wordnet.utils;

import java.util.*;

public class SAP {
    // Shortest possible length between two vertices in a digraph
    private int shortestLength;
    // Closest common ancestor to the given vertices
    private int commonAncestor;
    // Copy of the given digraph to track the ancestor of each vertex
    private DiGraph copyGraph;
    // Distance to vertex1 from common ancestor
    private int[] distanceTo1;
    // Distance to vertex2 from common ancestor
    private int[] distanceTo2;
    // List to keep track of nodes visited between vertex 1 to common ancestor
    private boolean[] visited1;
    // List to keep track of nodes visited between vertex 2 to common ancestor
    private boolean[] visited2;
    // Stack to maintain the path from vertex 1 to common ancestor
    private Stack<Integer> stack1;
    // Stack to maintain the path from vertex 2 to common ancestor
    private Stack<Integer> stack2;

    // Constructor
    public SAP(DiGraph G) {

        // checking if the graph is null
        if (G == null) {
            throw new IllegalArgumentException(" The argument to SAP is null");
        }
        // initializing data members
        copyGraph = new DiGraph(G);
        distanceTo1 = new int[G.noVertices()];
        distanceTo2 = new int[G.noVertices()];
        visited1 = new boolean[G.noVertices()];
        visited2 = new boolean[G.noVertices()];
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();

    }

    private void bfs(Queue<Integer> q1, Queue<Integer> q2) {
        // using bfs on the graph to reach the least common ancestor
        // Parameters :
        // q1 : a queue which stores all vertices visited from vertex 1
        // q2 : a queue which stores all vertices visited from vertex 2

        // checking if either of queues is not empty
        while (!q1.isEmpty() || !q2.isEmpty()) {
            // checking if q1 is not empty
            if (!q1.isEmpty()) {
                int v = q1.poll();
                int s = distanceTo1[v] + distanceTo2[v];
                // checks if the current vertex exists in path of vertex 2
                if (visited2[v]) {
                    // checks if the length is better than the value of shortestLength
                    if (s < shortestLength || shortestLength == -1) {
                        commonAncestor = v;
                        shortestLength = s;
                    }
                }
                // checks if the distance to vertex 1 is better than the value of shortestLength
                if (distanceTo1[v] < shortestLength || shortestLength == -1) {
                    for (int a : copyGraph.adjacentVertices(v)) {
                        // checks if the vertex exists in path of vertex 1
                        if (!visited1[a]) {
                            // add the vertex to the path and update the distance
                            distanceTo1[a] = distanceTo1[v] + 1;
                            visited1[a] = true;
                            stack1.push(a);
                            q1.add(a);
                        }
                    }
                }
            }
            // checking if q2 is not empty
            if (!q2.isEmpty()) {
                int v = q2.poll();
                int s = distanceTo1[v] + distanceTo2[v];
                // checks if the current vertex exists in path of vertex 1
                if (visited1[v]) {
                    // checks if the length is better than the value of shortestLength
                    if (s < shortestLength || shortestLength == -1) {
                        commonAncestor = v;
                        shortestLength = s;
                    }
                }
                // checks if the distance to vertex 2 is better than the value of shortestLength
                if (distanceTo2[v] < shortestLength || shortestLength == -1) {
                    for (int a : copyGraph.adjacentVertices(v)) {
                        // checks if the vertex exists in path of vertex 2
                        if (!visited2[a]) {
                            // add the vertex to the path and update the distance
                            distanceTo2[a] = distanceTo2[v] + 1;
                            visited2[a] = true;
                            stack2.push(a);
                            q2.add(a);
                        }
                    }
                }
            }
        }
        // reinitialize the path to default
        init();

    }

    private void init() {
        // initializing path tracing data members to default values

        // checking if the stack is not empty
        while (!stack1.isEmpty()) {
            int x = stack1.pop();
            visited2[x] = false;
        }
        // checking if the stack is not empty
        while (!stack2.isEmpty()) {
            int x = stack2.pop();
            visited2[x] = false;
        }

    }

    private void compute(int v, int w) {
        // function which helps in computing the distance and ancestor
        // Parameters :
        // v : a given vertex in integer form
        // w : a given vertex in integer form

        // initializing the parameters to be searched
        shortestLength = -1;
        commonAncestor = -1;
        distanceTo1[v] = 0;
        distanceTo2[w] = 0;
        // adding the vertices to their respective paths
        visited1[v] = true;
        visited2[w] = true;
        stack1.push(v);
        stack2.push(w);
        Queue<Integer> q1 = new PriorityQueue<>(); // queue to maintain the path from vertex 1
        Queue<Integer> q2 = new PriorityQueue<>(); // queue to maintain the path from vertex 2
        q1.add(v);
        q2.add(w);
        // performing bfs on the graph to find the values required
        bfs(q1, q2);

    }

    private void compute(Iterable<Integer> v, Iterable<Integer> w) {
        // function which helps in computing the distance and ancestor
        // Parameters :
        // v : a given vertex in Iterable form
        // w : a given vertex in Iterable form

        // initializing the parameters to be searched
        shortestLength = -1;
        commonAncestor = -1;
        // adding the vertices to their respective paths
        Queue<Integer> q1 = new PriorityQueue<>(); // queue to maintain the path from vertex 1
        Queue<Integer> q2 = new PriorityQueue<>(); // queue to maintain the path from vertex 2
        for (int v1 : v) { // iterating through vertices to be added in the path 1
            visited1[v1] = true;
            stack1.push(v1);
            distanceTo1[v1] = 0;
            q1.add(v1);
        }
        for (int w1 : w) { // iterating through vertices to be added in the path 2
            visited2[w1] = true;
            stack2.push(w1);
            distanceTo2[w1] = 0;
            q2.add(w1);
        }
        // performing bfs on the graph to find the values required
        bfs(q1, q2);

    }

    public int length(int v, int w) {
        // Parameters :
        // v : a given vertex in integer form
        // w : a given vertex in integer form
        // returns:
        // length of Shortest Ancestral Path

        // use the compute helper function
        compute(v, w);
        int curLength = shortestLength;
        shortestLength = -1;
        commonAncestor = -1;
        return curLength;

    }

    public int ancestor(int v, int w) {
        // Parameters :
        // v : a given vertex in integer form
        // w : a given vertex in integer form
        // returns:
        // Shortest common ancestor

        // use the compute helper function
        compute(v, w);
        int curAncestor = commonAncestor;
        shortestLength = -1;
        commonAncestor = -1;
        return curAncestor;

    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // Parameters :
        // v : a given vertex in Iterable form
        // w : a given vertex in Iterable form
        // returns:
        // length of Shortest Ancestral Path

        // use the compute helper function
        compute(v, w);
        int curLength = shortestLength;
        shortestLength = -1;
        commonAncestor = -1;
        return curLength;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // Parameters :
        // v : a given vertex in Iterable form
        // w : a given vertex in Iterable form
        // returns:
        // Shortest common ancestor

        // use the compute helper function
        compute(v, w);
        int curAncestor = commonAncestor;
        shortestLength = -1;
        commonAncestor = -1;
        return curAncestor;
    }

    public static void main(String[] args) {
        // driver for unit testing

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
        System.out.println("Length of shortest ancestral path is: " + sap.length(v, w));
        System.out.println("Shortest common ancestor is: " + sap.ancestor(v, w));
        In.close();
    }
}
