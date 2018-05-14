package assignment2;

import java.util.*;

public class StronglyConnected {

    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        int n = adj.length;
        Stack<Integer> stack = new Stack<>();
        boolean[] used = new boolean[n];
        ArrayList<Integer>[] reverseGraph = reverseGraph(adj);

        for (int v = 0; v < n; v++) {
            if (!used[v]) {
                dfs(reverseGraph, v, used, stack);
            }
        }

        int res = 0;
        used = new boolean[n];

        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!used[v]) {
                explore(adj, used, v);
                res++;
            }
        }
        return res;
    }

    private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] adj) {
        int n = adj.length;
        ArrayList<Integer>[] reversedGraph = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            reversedGraph[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> edges = adj[i];
            for (int j = 0; j < edges.size(); j++) {
                reversedGraph[edges.get(j)].add(i);
            }
        }
        return reversedGraph;
    }


    private static void dfs(ArrayList<Integer>[] adj, int vertex, boolean[] used, Stack<Integer> stack) {
        used[vertex] = true;
        for (int nextVertex : adj[vertex]) {
            if (!used[nextVertex]) {
                dfs(adj, nextVertex, used, stack);
            }
        }
        stack.push(vertex);
    }

    private static void explore(ArrayList<Integer>[] adj, boolean[] used, int vertex) {
        used[vertex] = true;
        for (int nextVertex : adj[vertex]) {
            if (!used[nextVertex]) {
                explore(adj, used, nextVertex);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = getGraph(n);
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(numberOfComponents(adj));

//        test();
    }

    private static ArrayList<Integer>[] getGraph(int n) {
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        return adj;
    }


    private static void test() {
        int n = 4;
        int m = 4;

        ArrayList<Integer>[] adj = getGraph(n);

        int[][] edges = new int[][]{{1, 2}, {4, 1}, {2, 3}, {3, 1}};
        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        System.out.println(numberOfComponents(adj));

        System.out.println();
        n = 5;
        m = 7;

        adj = getGraph(n);
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{2, 1}, {3, 2}, {3, 1}, {4, 3}, {4, 1}, {5, 2}, {5, 3}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }
        System.out.println(numberOfComponents(adj));
    }
}

