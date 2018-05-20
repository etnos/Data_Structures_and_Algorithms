import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class ShortestPaths {


    /**
     * Failed case #8/36: Wrong answer - Does not work
     *
     * @param adj
     * @param cost
     * @param s
     * @param distance
     * @param reachable
     * @param shortest
     */
    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost,
                                      int s, long[] distance, int[] reachable, int[] shortest) {
        //write your code here
        distance[s] = 0;
        reachable[s] = 1;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < adj.length; i++) {
            for (int u = 0; u < adj.length; u++) {
                for (int v : adj[u]) {
                    int v_index = adj[u].indexOf(v);
                    if (distance[u] != Long.MAX_VALUE && distance[v] > distance[u] + cost[u].get(v_index)) {
                        distance[v] = distance[u] + cost[u].get(v_index);
                        reachable[v] = 1;
                        if (i == adj.length - 1) {
                            queue.add(v);
                        }
                    }
                }
            }
        }

        int[] visited = new int[adj.length];
        while (!queue.isEmpty()) {
            int u = queue.remove();
            visited[u] = 1;
            if (u != s)
                shortest[u] = 0;
            for (int v : adj[u]) {
                if (visited[v] == 0) {
                    queue.add(v);
                    visited[v] = 1;
                    shortest[v] = 0;
                }
            }
        }
        distance[s] = 0;
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
        scanner.close();

//        test();
    }


    private static void test() {
        sunTest1();
        System.out.println();
        sunTest2();
    }

    private static void sunTest1() {
        int n = 6;
        int m = 7;

        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        int[][] data = new int[][]{{1, 2, 10}, {2, 3, 5}, {1, 3, 100}, {3, 5, 7}, {5, 4, 10}, {4, 3, -18}, {6, 1, -1}};

        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        int s = 1 - 1;

        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }

        shortestPaths(adj, cost, s, distance, reachable, shortest);

        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

    private static void sunTest2() {
        int n = 5;
        int m = 4;

        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        int[][] data = new int[][]{{1, 2, 1}, {4, 1, 2}, {2, 3, 2}, {3, 1, -5}};

        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        int s = 4 - 1;

        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }

        shortestPaths(adj, cost, s, distance, reachable, shortest);

        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }
}