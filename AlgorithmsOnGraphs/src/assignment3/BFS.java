package assignment3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        //write your code here
        return BFS(adj, s, t);
    }

    private static int BFS(ArrayList<Integer>[] adj, int s, int t) {
        LinkedList<Integer> queue1 = new LinkedList<>();
        LinkedList<Integer> queue2 = new LinkedList<>();
        LinkedList<Integer> tempQueue;
        int[] used = new int[adj.length];
        queue1.add(s);
        used[s] = 1;
        int distance = -1;
        int distanceCount = 0;
        boolean isFound = false;
        while (!queue1.isEmpty()) {
            int currentVertex = queue1.poll();
            if (currentVertex == t) {
                isFound = true;
                break;
            }

            ArrayList<Integer> edges = adj[currentVertex];
            for (Integer edge : edges) {
                if (used[edge] != 1) {
                    used[edge] = 1;
                    queue2.add(edge);
                }
            }

            if (queue1.isEmpty()) {
                tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                // increase distance
                distanceCount++;
            }
        }

        if (isFound) {
            distance = distanceCount;
        }

        return distance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = getGraph(n);
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));

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
            adj[y - 1].add(x - 1);
        }

        x = 2;
        y = 4;
        System.out.println(distance(adj, x - 1, y - 1));

        System.out.println();

        n = 5;
        m = 4;

        adj = getGraph(n);

        edges = new int[][]{{5, 2}, {1, 3}, {3, 4}, {1, 4}};

        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }

        x = 3;
        y = 5;
        System.out.println(distance(adj, x - 1, y - 1));
    }
}

