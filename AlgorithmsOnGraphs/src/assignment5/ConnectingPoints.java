package assignment5;

import java.util.PriorityQueue;
import java.util.Scanner;

public class ConnectingPoints {
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.0;
        int n = x.length;
        DisjointSetsKruskal sets = new DisjointSetsKruskal(n);

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>((edge1, edge2) -> {
            if (edge1.weight == edge2.weight) {
                return 0;
            } else {
                return edge1.weight < edge2.weight ? -1 : 1;
            }
        });

        for (int i = 0; i < n; i++) {
            sets.makeSet(i);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                priorityQueue.offer(new Edge(i, j, distance(x[i], y[i], x[j], y[j])));
            }
        }

        while (!priorityQueue.isEmpty()) {
            Edge lightestEdge = priorityQueue.poll();
            if (sets.find(lightestEdge.u) != sets.find(lightestEdge.v)) {
                sets.union(lightestEdge.u, lightestEdge.v);
                result += lightestEdge.weight;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));

//        test();
    }


    private static void test() {
        int n = 4;
        int[] x = new int[n];
        int[] y = new int[n];
        int[][] data = new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }

        System.out.println(minimumDistance(x, y));

        n = 5;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{0, 0}, {0, 2}, {1, 1}, {3, 0}, {3, 2}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }

        System.out.println(minimumDistance(x, y));
    }

    private static class DisjointSetsKruskal {
        int[] parents;
        int[] ranks;

        public DisjointSetsKruskal(int size) {
            parents = new int[size];
            ranks = new int[size];
        }

        public void makeSet(int i) {
            parents[i] = i;
            ranks[i] = 1;
        }

        public int find(int i) {
            if (parents[i] == i) {
                return i;
            }
            parents[i] = find(parents[i]);
            return parents[i];
        }

        public void union(int i, int j) {
            int r1 = find(i);
            int r2 = find(j);
            if (r1 == r2) {
                return;
            }
            if (ranks[r1] < ranks[r2]) {
                parents[r1] = r2;
            } else if (ranks[r2] < ranks[r1]) {
                parents[r2] = r1;
            } else {
                parents[r2] = r1;
                ranks[r1] += 1;
            }
        }
    }

    private static class Edge {
        int u, v;
        double weight;

        public Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    private static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}

