package assignment1;

import java.io.*;
import java.util.*;

public class Evacuation {
    private static FastScanner in;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();

        FlowGraph graph = readGraph();
        System.out.println(maxFlow(graph, 0, graph.size() - 1));

//        test();
    }

    /**
     * Edmonds-Karp algorithm
     *
     * @param graph
     * @param from
     * @param to
     * @return
     */
    private static int maxFlow(FlowGraph graph, int from, int to) {
        int flow = 0;
        /* your code goes here */
        FlowPath flowPath = new FlowPath();
        while (flowPath != null) {
            while (!flowPath.edges.isEmpty()) {
                Integer edgeNum = flowPath.edges.pop();
                graph.addFlow(edgeNum, flowPath.flow);
            }
            flow = flow + flowPath.flow;
            flowPath = getPath(graph);
        }
        return flow;
    }

    private static FlowPath getPath(FlowGraph flowGraph) {
        FlowPath flowPath = new FlowPath();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int s = flowGraph.size() - 1;
        Integer[] prevEdge = new Integer[s + 1];
        priorityQueue.add(0);
        while (!priorityQueue.isEmpty()) {
            for (int edgeNum : flowGraph.graph[priorityQueue.poll()]) {
                Edge e = flowGraph.getEdge(edgeNum);
                int cap = e.capacity - e.flow;
                int to = e.to;
                if (prevEdge[to] == null && cap > 0) {
                    priorityQueue.add(to);
                    prevEdge[to] = edgeNum;
                    if (to == s) {
                        int backTrack = to;
                        flowPath.flow = Integer.MAX_VALUE;
                        do {
                            Integer peNum = prevEdge[backTrack];
                            Edge pe = flowGraph.getEdge(peNum);
                            int availFlow = pe.capacity - pe.flow;
                            if (availFlow < flowPath.flow)
                                flowPath.flow = availFlow;
                            flowPath.edges.add(peNum);
                            backTrack = pe.from;
                        } while (backTrack > 0);
                        return flowPath;
                    }
                }
            }

        }

        return null;
    }

    static class FlowPath {
        Stack<Integer> edges;
        int flow = 0;

        public FlowPath() {
            edges = new Stack<>();
        }
    }

    static FlowGraph readGraph() throws IOException {
        int vertex_count = in.nextInt();
        int edge_count = in.nextInt();
        FlowGraph graph = new FlowGraph(vertex_count);

        for (int i = 0; i < edge_count; ++i) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1, capacity = in.nextInt();
            graph.addEdge(from, to, capacity);
        }
        return graph;
    }

    static class Edge {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    /* This class implements a bit unusual scheme to store the graph edges, in order
     * to retrieve the backward edge for a given edge quickly. */
    static class FlowGraph {
        /* List of all - forward and backward - edges */
        private List<Edge> edges;

        /* These adjacency lists store only indices of edges from the edges list */
        private List<Integer>[] graph;

        public FlowGraph(int n) {
            this.graph = (ArrayList<Integer>[]) new ArrayList[n];
            for (int i = 0; i < n; ++i)
                this.graph[i] = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

        public void addEdge(int from, int to, int capacity) {
            /* Note that we first append a forward edge and then a backward edge,
             * so all forward edges are stored at even indices (starting from 0),
             * whereas backward edges are stored at odd indices. */
            Edge forwardEdge = new Edge(from, to, capacity);
            Edge backwardEdge = new Edge(to, from, 0);
            graph[from].add(edges.size());
            edges.add(forwardEdge);
            graph[to].add(edges.size());
            edges.add(backwardEdge);
        }

        public int size() {
            return graph.length;
        }

        public List<Integer> getIds(int from) {
            return graph[from];
        }

        public Edge getEdge(int id) {
            return edges.get(id);
        }

        public void addFlow(int id, int flow) {
            /* To get a backward edge for a true forward edge (i.e id is even), we should get id + 1
             * due to the described above scheme. On the other hand, when we have to get a "backward"
             * edge for a backward edge (i.e. get a forward edge for backward - id is odd), id - 1
             * should be taken.
             *
             * It turns out that id ^ 1 works for both cases. Think this through! */
            edges.get(id).flow += flow;
            edges.get(id ^ 1).flow -= flow;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static FlowGraph readTestGraph() throws IOException {
        int vertex_count = 5;
        int edge_count = 7;
        FlowGraph graph = new FlowGraph(vertex_count);

        int[][] data = new int[][]{{1, 2, 2}, {2, 5, 5}, {1, 3, 6}, {3, 4, 2}, {4, 5, 1}, {3, 2, 3}, {2, 4, 1}};
        for (int i = 0; i < edge_count; ++i) {
            int from = data[i][0] - 1, to = data[i][1] - 1, capacity = data[i][2];
            graph.addEdge(from, to, capacity);
        }
        return graph;
    }

    static FlowGraph readTestGraph1() throws IOException {
        int vertex_count = 4;
        int edge_count = 5;
        FlowGraph graph = new FlowGraph(vertex_count);

        int[][] data = new int[][]{{1, 2, 10000}, {1, 3, 10000}, {2, 3, 1}, {3, 4, 10000}, {2, 4, 10000}};
        for (int i = 0; i < edge_count; ++i) {
            int from = data[i][0] - 1, to = data[i][1] - 1, capacity = data[i][2];
            graph.addEdge(from, to, capacity);
        }
        return graph;
    }

    private static void test() throws IOException {
        FlowGraph graph = readTestGraph();
        System.out.println(maxFlow(graph, 0, graph.size() - 1));
        graph = readTestGraph1();
        System.out.println(maxFlow(graph, 0, graph.size() - 1));
    }
}
