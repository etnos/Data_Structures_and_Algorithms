import java.util.*;

public class ShortestPaths {

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost,
                                      int s, long[] distance, int[] reachable, int[] shortest) {
        int n = adj.length;
        distance[s] = 0;
        BellmanFord(adj, distance, cost);

        LinkedList<Integer> queue = new LinkedList<>();
        for (int u = 0; u < n; u++) {
            List<Integer> vertexNeighbours = adj[u];
            List<Integer> vertexCost = cost[u];
            for (int i = 0; i < vertexNeighbours.size(); i++) {
                relax(u, vertexNeighbours.get(i), vertexCost.get(i), distance, queue);
            }
        }

        HashSet<Integer> negativeCycle = BFS(queue, adj);

        for (int vertex : queue) {
            distance[vertex] = -1;
        }
        for (int v = 0; v < n; v++) {
            if (distance[v] != Long.MAX_VALUE) {
                reachable[v] = 1;
                if (v == s) {
                    shortest[v] = 1;
                } else {
                    shortest[v] = negativeCycle.contains(v) ? 0 : 1;
                }
            }
        }

        distance[s] = 0;
    }

    private static void BellmanFord(ArrayList<Integer>[] adj, long[] distance, ArrayList<Integer>[] cost) {
        int n = adj.length;
        for (int i = 1; i < n; i++) {
            for (int u = 0; u < n; u++) {
                ArrayList<Integer> vertices = adj[u];
                for (int j = 0; j < vertices.size(); j++) {
                    int v = vertices.get(j);
                    int uCost = cost[u].get(j);
                    relax(distance, uCost, u, v);
                }
            }
        }
    }

    private static boolean relax(long[] dist, int cost, int u, int v) {
        if (dist[u] == Integer.MAX_VALUE) {
            dist[u] = 0;
        }
        if (dist[v] > dist[u] + cost) {
            dist[v] = dist[u] + cost;
            return true;
        }
        return false;
    }

    private static void relax(int u, int vertex, int cost,
                              long[] distance, LinkedList<Integer> queue) {
        if (distance[u] != Long.MAX_VALUE && distance[vertex] > distance[u] + cost) {
            distance[vertex] = distance[u] + cost;
            queue.offer(vertex);
        }
    }

    private static HashSet<Integer> BFS(LinkedList<Integer> queue, List<Integer>[] adj) {
        HashSet<Integer> bfsSet = new HashSet<>();
        boolean[] visited = new boolean[adj.length];
        while (!queue.isEmpty()) {
            int u = queue.poll();
            bfsSet.add(u);
            visited[u] = true;
            for (int v : adj[u]) {
                if (!visited[v]) {
                    queue.offer(v);
                }
            }
        }
        return bfsSet;
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
    }

}

