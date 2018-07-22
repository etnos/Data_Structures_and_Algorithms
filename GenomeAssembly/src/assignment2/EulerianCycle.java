package assignment2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EulerianCycle {


    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new EulerianCycle().run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        Vertex[] graph = reader();

        if (isEulerianCycle(graph)) {
            System.out.println(1);
            ArrayList<Integer> cycle = findCycle(graph);
            printEulerCycle(cycle);
        } else {
            System.out.println(0);
        }
    }

    public static Vertex[] reader() {
        Scanner scanner = new Scanner(System.in);

        int verticesCount = scanner.nextInt();
        int edgesCount = scanner.nextInt();

        Vertex[] graph = new Vertex[verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            graph[i] = new Vertex(i);
        }

        for (int i = 0; i < edgesCount; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            Edge edge = new Edge(x - 1, y - 1);
            graph[x - 1].edgeList.add(edge);
            graph[y - 1].inDegree++;
        }

        return graph;
    }

//    public static void main(String[] args) {
//
//        test1();
//        System.out.println();
//        test2();
//        System.out.println();
//        test3();
//    }

    private static void test1() {
        int verticesCount = 3;
        int edgesCount = 4;

        int[][] data = new int[][]{{2, 3}, {2, 2}, {1, 2}, {3, 1}};

        Vertex[] graph = new Vertex[verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            graph[i] = new Vertex(i);
        }

        for (int i = 0; i < data.length; i++) {
            Edge edge = new Edge(data[i][0] - 1, data[i][1] - 1);
            graph[data[i][0] - 1].edgeList.add(edge);
            graph[data[i][1] - 1].inDegree++;
        }

        if (isEulerianCycle(graph)) {
            System.out.println(1);
            ArrayList<Integer> cycle = findCycle(graph);
            printEulerCycle(cycle);
        } else {
            System.out.println(0);
        }
    }

    private static void test2() {
        int verticesCount = 3;
        int edgesCount = 4;

        int[][] data = new int[][]{{1, 3}, {2, 3}, {1, 2}, {3, 1}};

        Vertex[] graph = new Vertex[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            graph[i] = new Vertex(i);
        }

        for (int i = 0; i < data.length; i++) {
            Edge edge = new Edge(data[i][0] - 1, data[i][1] - 1);
            graph[data[i][0] - 1].edgeList.add(edge);
            graph[data[i][1] - 1].inDegree++;
        }

        if (isEulerianCycle(graph)) {
            System.out.println(1);
            ArrayList<Integer> cycle = findCycle(graph);
            printEulerCycle(cycle);
        } else {
            System.out.println(0);
        }
    }

    private static void test3() {
        int verticesCount = 4;
        int edgesCount = 7;

        int[][] data = new int[][]{{1, 2}, {2, 1}, {1, 4}, {4, 1}, {2, 4}, {3, 2}, {4, 3}};

        Vertex[] graph = new Vertex[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            graph[i] = new Vertex(i);
        }

        for (int i = 0; i < data.length; i++) {
            Edge edge = new Edge(data[i][0] - 1, data[i][1] - 1);
            graph[data[i][0] - 1].edgeList.add(edge);
            graph[data[i][1] - 1].inDegree++;
        }

        if (isEulerianCycle(graph)) {
            System.out.println(1);
            ArrayList<Integer> cycle = findCycle(graph);
            printEulerCycle(cycle);
        } else {
            System.out.println(0);
        }
    }

    /**
     * This function returns true if the directed graph has an eulerian
     * cycle, otherwise returns false
     * Eulerian graph = in degree == out degree and is a strongly connected graph
     *
     * in this assignment the graph is strongly connected by default, do not need to check
     * @param graph
     * @return
     */
    private static boolean isEulerianCycle(Vertex[] graph) {
        // Check if in degree and out degree of every vertex is same
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].inDegree != graph[i].edgeList.size()) {
                return false;
            }
        }

        return true;
    }

    //function to find an eulerian cycle in the graph.
    public static ArrayList<Integer> findCycle(Vertex[] graph) {
        ArrayList<Integer> cycle = new ArrayList<>();
        explore(graph, 0, cycle);

        return cycle;
    }

    //function finds the eulerian cycle.
    private static void explore(Vertex[] graph, int vertex, List<Integer> cycle) {
        List<Edge> edgeList = graph[vertex].edgeList;
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            if (!edge.used) {
                edge.used = true;
                explore(graph, edge.to, cycle);
            }
        }
        cycle.add(vertex);
    }

    private static void printEulerCycle(ArrayList<Integer> cycle) {
        // print in revers order and skip last element because it is the same as the first one
        int size = cycle.size() - 1;
        for (int i = 0; i < size; i++) {
            System.out.print((cycle.get(size - i) + 1) + " ");
        }
    }

    //class for a vertex of the graph.
    static class Vertex {
        int vertexNum;            //if of the vertex.
        int inDegree;             // maintaining in degree
        List<Edge> edgeList = new ArrayList<>();        //list of adjacent vertices.

        public Vertex(int vertexNum) {
            this.vertexNum = vertexNum;
        }
    }

    //class for a edge of the graph.
    static class Edge {
        int from;            //start vertex of the edge.
        int to;                //end vertex of the edge.
        boolean used;            //check if edge is used while finding eulerian circuit in the graph.

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
            this.used = false;
        }
    }
}
