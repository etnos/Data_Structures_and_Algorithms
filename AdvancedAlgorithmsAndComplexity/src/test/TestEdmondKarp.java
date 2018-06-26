package test;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class TestEdmondKarp {

    public static void main(String[] args) {
        int vertex_count = 5;
        int edge_count = 7;
        int[][] data = new int[][]{{1, 2, 2}, {2, 5, 5}, {1, 3, 6}, {3, 4, 2}, {4, 5, 1}, {3, 2, 3}, {2, 4, 1}};
        FlowGraph flowGraph = new FlowGraph(vertex_count);
        int from, to, capacity;
        for (int i = 0; i < data.length; i++) {
            from = data[i][0] - 1;
            to = data[i][1] - 1;
            capacity = data[i][2];

            flowGraph.addEdge(from, to, capacity);
        }


        System.out.println();
    }


    static int maxFlow(FlowGraph flowGraph, int from, int to) {

        return 0;
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

    static class Edge {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    static class FlowGraph {

        /* List of all - forward and backward - edges */
        private List<Edge> edges;

        /* These adjacency lists store only indices of edges from the edges list */
        private List<Integer>[] graph;

        public FlowGraph(int n) {
            graph = new ArrayList[n];
            edges = new ArrayList<>();
            for (int i = 0; i < graph.length; i++) {
                graph[i] = new ArrayList<>();
            }
        }

        public void addEdge(int from, int to, int capacity) {
            Edge forwardEdge = new Edge(from, to, capacity);
            Edge backwardEdge = new Edge(from, to, capacity);

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


}
