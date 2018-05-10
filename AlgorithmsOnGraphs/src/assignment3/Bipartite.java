package assignment3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Bipartite {
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
        return BFS(adj);
    }

    private static int BFS(ArrayList<Integer>[] adj) {
        LinkedList<Integer> queue1 = new LinkedList<>();
        LinkedList<Integer> queue2 = new LinkedList<>();
        LinkedList<Integer> tempQueue;

        int[] used = new int[adj.length];
        int[] usedColor = new int[adj.length];

        queue1.add(0);
        used[0] = 1;
        int WHITE = 1;
        int BLACK = 2;
        int color;

        color = WHITE;
        usedColor[0] = color;

        while (!queue1.isEmpty()) {
            int currentVertex = queue1.poll();

            ArrayList<Integer> edges = adj[currentVertex];
            for (Integer edge : edges) {

                if (color == WHITE) {
                    if (usedColor[edge] == WHITE) {
                        return 0;
                    }
                    usedColor[edge] = BLACK;
                } else {
                    if (usedColor[edge] == BLACK) {
                        return 0;
                    }
                    usedColor[edge] = WHITE;
                }


                if (used[edge] != 1) {
                    used[edge] = 1;
                    queue2.add(edge);
                }
            }

            if (queue1.isEmpty()) {
                tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                // switch color
                if (color == WHITE) {
                    color = BLACK;
                } else {
                    color = WHITE;
                }
            }
        }

        return 1;
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
        System.out.println(bipartite(adj));

//        test();
    }

    private static ArrayList<Integer>[] getGraph(int n) {
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
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

        System.out.println(bipartite(adj));

        System.out.println();

        n = 5;
        m = 4;

        adj = getGraph(n);

        edges = new int[][]{{5, 2}, {4, 2}, {3, 4}, {1, 4}};

        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }

        System.out.println(bipartite(adj));
    }

}

