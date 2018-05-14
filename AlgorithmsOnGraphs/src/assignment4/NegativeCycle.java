package assignment4;

import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {

    private static int BellmanFord(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        int[] dist = new int[adj.length];
        int[] prev = new int[adj.length];
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }

        for (int i = 1; i < adj.length; i++) {
            for (int u = 0; u < adj.length; u++) {
                ArrayList<Integer> vertices = adj[u];
                for (int j = 0; j < vertices.size(); j++) {
                    int v = vertices.get(j);
                    int uCost = cost[u].get(j);
                    relax(dist, prev, uCost, u, v);
                }
            }
        }

        for (int u = 0; u < adj.length; u++) {
            ArrayList<Integer> vertices = adj[u];
            for (int j = 0; j < vertices.size(); j++) {
                int v = vertices.get(j);
                int uCost = cost[u].get(j);
                if (relax(dist, prev, uCost, u, v)) {
                    return 1;
                }
            }
        }

        return 0;
    }


    private static boolean relax(int[] dist, int[] prev, int cost, int u, int v) {
        if (dist[u] == Integer.MAX_VALUE) {
            dist[u] = 0;
        }
        if (dist[v] > dist[u] + cost) {
            dist[v] = dist[u] + cost;
            prev[v] = u;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(BellmanFord(adj, cost));

//        test();
    }

    private static void test() {
        int n = 4;
        int m = 4;

        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }

        int[][] data = new int[][]{{1, 2, -5}, {4, 1, 2}, {2, 3, 2}, {3, 1, 1}};
        int x, y, w;
        for (int i = 0; i < m; i++) {
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }

        System.out.println("negativeCycle " + BellmanFord(adj, cost));

    }
}

