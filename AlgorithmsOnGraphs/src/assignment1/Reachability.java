package assignment1;

import java.util.*;

public class Reachability {
    private static int reach(HashSet<Integer>[] adj, int x, int y) {
        //write your code here
        if (adj == null || adj.length == 0) {
            return 0;
        }

        HashSet<Integer> visited = new HashSet<>();
        LinkedList<Integer> queue = new LinkedList<>();
        queue.push(x);
        visited.add(x);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            HashSet<Integer> currentVertexEdges = adj[current];
            if (currentVertexEdges.contains(y)) {
                return 1;
            } else {
                for (int e : currentVertexEdges) {
                    if (!visited.contains(e)) {
                        queue.push(e);
                        visited.add(e);
                    }
                }

            }
        }

        return 0;
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
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
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
        int m = 4;

        HashSet<Integer>[] adj = getGraph(n);
        int[][] edges = new int[][]{{1, 2}, {3, 2}, {4, 3}, {1, 4}};

        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }

        x = 1 - 1;
        y = 4 - 1;

        System.out.println(reach(adj, x, y));


        System.out.println("-----------------------");
        n = 4;
        m = 2;

        adj = getGraph(n);
        edges = new int[][]{{1, 2}, {3, 2}};

        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }

        x = 1 - 1;
        y = 4 - 1;

        System.out.println(reach(adj, x, y));

        System.out.println("End");
    }
}

