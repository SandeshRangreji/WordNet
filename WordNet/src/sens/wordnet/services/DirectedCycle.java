package sens.wordnet.services;

import java.util.Scanner;
import java.util.Stack;

public class DirectedCycle {
    private boolean[] visited; // list of visited vertices
    private boolean[] recStack; // recursion stack
    private Stack<Integer> cycle;
    private int[] parent; // list of parent vertex to each vertex in a path

    public DirectedCycle(DiGraph G){
        int n = G.noVertices();
        visited = new boolean[n];
        recStack = new boolean[n];
        parent = new int[n];
        // initialize all lists
        for (int v = 0; v < n; v++){
            if (!visited[v] && cycle == null)
                DFS(G, v);
        }
    }

    public void DFS(DiGraph G, int u){
        visited[u] = true;
        recStack[u] = true;
        for (int v : G.adjacentVertices(u)){
            if (cycle != null) return;
            else if(!visited[v]){
                parent[v] = u;
                DFS(G, v);
            }
            else if (recStack[v]){
                cycle = new Stack<Integer>();
                for(int x = u; x != v; x = parent[x]){
                    cycle.push(x);
                }
                cycle.push(v);
                cycle.push(u);
            }
        }
        recStack[u] = false;
    }

    public boolean hasCycle(){
        return cycle != null;
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }

    public boolean checkCycle(){
        if(hasCycle()){
            int first = -1;
            int last = -1;
            for (int v : cycle()) {
                if(first == -1)
                    first = v;
                last = v;
            }
            if(first != last){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner In = new Scanner(System.in);
        System.out.print("Let's make a digraph. \nEnter no. of vertices: ");
        int n = In.nextInt();
        DiGraph graph = new DiGraph(n);
        for(int u = 0; u < n; u++) {
            System.out.print("Enter the no. of connected vertices for vertex " + u +": ");
            int oDegree = In.nextInt();
            for(int i = 0; i < oDegree; i++) {
                System.out.print("Enter connected vertex-" + (i+1) + ": ");
                int v = In.nextInt();
                graph.addEdge(u, v);
            }
        }
        In.close();

        DirectedCycle directedCycle = new DirectedCycle(graph);
        if(directedCycle.checkCycle()){
            System.out.println("The last directed cycle in this graph is: ");
            for (int v : directedCycle.cycle()){
                System.out.println(v);
            }
        }
        else{
            System.out.println("This graph doesn't contain a directed cycle");
        }
    }
}