package assignment5;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Clustering {
    private static double clustering(int[] x, int[] y, int k) {
        int n = x.length;
        DisjointSetsKruskal disjointSetsKruskal = new DisjointSetsKruskal(n);
        Comparator<Edge> edgeComparator = (edge1, edge2) -> {
            if (edge1.weight == edge2.weight) {
                return 0;
            } else {
                return edge1.weight < edge2.weight ? -1 : 1;
            }
        };
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(edgeComparator);


        for (int i = 0; i < n; i++) {
            disjointSetsKruskal.makeSet(i);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                priorityQueue.offer(new Edge(i, j, distance(x[i], y[i], x[j], y[j])));
            }
        }
        PriorityQueue<Edge> mst = new PriorityQueue<>(k - 1, Collections.reverseOrder(edgeComparator));
        while (!priorityQueue.isEmpty()) {
            Edge lightest = priorityQueue.poll();
            if (disjointSetsKruskal.find(lightest.u) != disjointSetsKruskal.find(lightest.v)) {
                disjointSetsKruskal.union(lightest.u, lightest.v);
                mst.offer(lightest);
            }
        }
        for (int i = 1; i <= k - 2; i++) {
            mst.poll();
        }
        return mst.peek().weight;
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
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
//        test();
    }


    private static void test() {
        int n = 12;
        int[] x = new int[n];
        int[] y = new int[n];
        int[][] data = new int[][]{{7, 6}, {4, 3}, {5, 1}, {1, 7}, {2, 7}, {5, 7}, {3, 3}, {7, 8}, {2, 8}, {4, 4}, {6, 7}, {2, 6}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }

        int k = 3;
        System.out.println(clustering(x, y, k));

        n = 8;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{3, 1}, {1, 2}, {4, 6}, {9, 8}, {9, 9}, {8, 9}, {3, 11}, {4, 12}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }

        k = 4;
        System.out.println(clustering(x, y, k));
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

