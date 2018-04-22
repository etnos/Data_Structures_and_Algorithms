package assignment1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class ConnectedComponents {
    private static int numberOfComponents(HashSet<Integer>[] adj) {
        int result = 0;
        //write your code here
        HashSet<Integer> visited = new HashSet<>();
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < adj.length; i++) {
            if (!visited.contains(i)) {
                result++;
                visited.add(i);
                queue.push(i);
                explore(visited, queue, adj);

            }
        }

        return result;
    }

    private static void explore(HashSet<Integer> visited, LinkedList<Integer> queue, HashSet<Integer>[] adj) {
        while (!queue.isEmpty()) {
            int current = queue.poll();
            HashSet<Integer> currentVertexEdges = adj[current];
            for (int e : currentVertexEdges) {
                if (!visited.contains(e)) {
                    queue.push(e);
                    visited.add(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        mainStarter();
//        test();
    }

    private static void mainStarter() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        HashSet<Integer>[] adj = getGraph(n);
        for (int i = 0; i < n; i++) {
            adj[i] = new HashSet<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(numberOfComponents(adj));
    }

    private static HashSet<Integer>[] getGraph(int n) {
        HashSet<Integer>[] adj = (HashSet<Integer>[]) new HashSet[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new HashSet<Integer>();
        }
        return adj;
    }

    private static void test() {
        int n = 4;
        int m = 2;

        HashSet<Integer>[] adj = getGraph(n);
        int[][] edges = new int[][]{{1, 2}, {3, 2}};

        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }

        System.out.println(numberOfComponents(adj));

        System.out.println("End");
    }
}

