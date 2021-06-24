package sens.wordnet.services;

import java.util.Stack;

public class DirectedCycle {
    private boolean[] visited;
    private boolean[] recStack;
    private Stack<Integer> cycle;
    private int[] parent;

    public DirectedCycle(DiGraph G){
        int n = G.noVertices();
        visited = new boolean[n];
        recStack = new boolean[n];
        parent = new int[n];
        for (int v = 0; v < n; v++){
            if (!visited[v] && cycle == null)
                DFS(G, v);
        }
    }

    public void DFS(DiGraph G, int u){
        visited[u] = true;
        recStack[u] = true;
        for (int v : G.adjacentVertices(u)){
            if(!visited[v]){
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
}