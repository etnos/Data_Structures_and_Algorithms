package assignment4;

import java.util.*;


public class ShortestPaths {

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost,
                                      int s, long[] distance, int[] reachable, int[] shortest) {
        //write your code here
        distance[s] = 0;
        List<Integer> reachableVertex = dfs(adj, s);
        for (Integer index : reachableVertex) {
            reachable[index] = 1;
        }
        List<Integer> cycle = new ArrayList<>();

        for (int i = 0; i < adj.length; i++) {
            for (int j = 0; j < reachableVertex.size(); j++) {
                Integer currentVertex = reachableVertex.get(j);
                List<Integer> neighbours = adj[currentVertex];
                List<Integer> neighboursCosts = cost[currentVertex];
                for (int k = 0; k < neighbours.size(); k++) {
                    Integer currentNeighbour = neighbours.get(k);
                    Integer currentCost = neighboursCosts.get(k);

                    if (distance[currentNeighbour] > distance[currentVertex] + currentCost) {
                        distance[currentNeighbour] = distance[currentVertex] + currentCost;
                        if (i == adj.length - 1) {
                            shortest[currentNeighbour] = 0;
                            cycle.add(currentNeighbour);
                        }
                    }
                }
            }

        }
        Set<Integer> allNonReachable = new HashSet<>();
        if(!cycle.isEmpty()) {
            allNonReachable.addAll(dfs(adj, cycle.get(0)));
            for (Integer index : allNonReachable) {
                shortest[index] = 0;
            }
        }
    }

    public static List<Integer> dfs(ArrayList<Integer>[] adj, int s) {
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adj.length];

        queue.add(s);
        while (!queue.isEmpty()) {
            Integer currentVertex = queue.poll();
            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                result.add(currentVertex);
                List<Integer> neighbours = adj[currentVertex];
                for (Integer neighbour : neighbours) {
                    queue.add(neighbour);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        mainCoursera();
//        test();
    }

    private static void mainCoursera() {
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