package assignment4;

import java.util.*;

public class Dijkstra {

    private static boolean relax(int[] dist, int cost, int u, int v) {
        if (dist[u] == Integer.MAX_VALUE) {
            dist[u] = 0;
        }
        if (dist[v] > dist[u] + cost) {
            dist[v] = dist[u] + cost;
            return true;
        }
        return false;
    }

    private static int dijkstra(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        int[] dist = new int[adj.length];
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(adj.length);
        queue.add(s);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            ArrayList<Integer> edges = adj[vertex];
            ArrayList<Integer> edgesCosts = cost[vertex];
            for (int i = 0; i < edges.size(); i++) {
                int edge = edges.get(i);
                if (relax(dist, edgesCosts.get(i), vertex, edge)) {
                    queue.add(edge);
                }
            }
        }

        if (dist[t] == Integer.MAX_VALUE) {
            return -1;
        } else {
            return dist[t];
        }
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(dijkstra(adj, cost, x, y));

//        test();

//        autoTest();
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

        int[][] data = new int[][]{{1, 2, 1}, {4, 1, 2}, {2, 3, 2}, {1, 3, 5}};
        int x, y, w;
        for (int i = 0; i < m; i++) {
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        x = 1 - 1;
        y = 3 - 1;

        System.out.println("dijkstra " + dijkstra(adj, cost, x, y));

        n = 5;
        m = 9;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }

        data = new int[][]{{1, 2, 4}, {1, 3, 2}, {2, 3, 2}, {3, 2, 1}, {2, 4, 2}, {3, 5, 4}, {5, 4, 1}, {2, 5, 3}, {3, 4, 4}};

        for (int i = 0; i < m; i++) {
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        x = 1 - 1;
        y = 5 - 1;

        System.out.println("dijkstra " + dijkstra(adj, cost, x, y));

        n = 3;
        m = 3;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }

        data = new int[][]{{1, 2, 7}, {1, 3, 5}, {2, 3, 2}};

        for (int i = 0; i < m; i++) {
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        x = 3 - 1;
        y = 2 - 1;

        System.out.println("dijkstra " + dijkstra(adj, cost, x, y));

        n = 3;
        m = 2;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }

        data = new int[][]{{1, 2, 7}, {2, 3, 5}};

        for (int i = 0; i < m; i++) {
            x = data[i][0];
            y = data[i][1];
            w = data[i][2];
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        x = 0;
        y = 0;

        System.out.println("dijkstra " + dijkstra(adj, cost, x, y));
    }


    private static void autoTest() {
        Random rand = new Random();
        while (true) {
            int n = 3;//2 + rand.nextInt(5);
            int m = 5;//4 + rand.nextInt(10);

            ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
            ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<Integer>();
                cost[i] = new ArrayList<Integer>();
            }


            int[][] data = new int[m][3]; //{{1, 2, 1}, {4, 1, 2}, {2, 3, 2}, {1, 3, 5}};
            for (int i = 0; i < m; i++) {
                data[i][0] = n - rand.nextInt(n);
                data[i][1] = n - rand.nextInt(n);
                data[i][2] = 1 + rand.nextInt(10);
            }
            int x, y, w;
            for (int i = 0; i < m; i++) {
                x = data[i][0];
                y = data[i][1];
                w = data[i][2];
                if (x == n) {
                    x--;
                }
                if (y == n) {
                    y--;
                }
                adj[x].add(y);
                cost[x].add(w);
            }
            x = n - rand.nextInt(n);
            y = n - rand.nextInt(n);

            if (x == n) {
                x--;
            }
            if (y == n) {
                y--;
            }

//            int dijkstra = distance(adj, cost, x, y);
//            int naive = naive(adj, cost, x, y); // No naive implementation
//            if (dijkstra != naive) {
//                System.out.print("dijkstra " + dijkstra + " naive " + naive);
//                System.out.println();
//            }
        }

    }
}

