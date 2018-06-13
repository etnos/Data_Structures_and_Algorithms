import java.io.*;
import java.util.*;

public class AirlineCrews_MaxMatching {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
//        new AirlineCrews_MaxMatching().solve();
        new AirlineCrews_MaxMatching().test();
    }

    private void test() throws IOException {
        boolean[][] bipartiteGraph = readDataTest1();
        int[] matching = findMatching(bipartiteGraph);
        writeResponseTest(matching);

        System.out.println();

        bipartiteGraph = readDataTest2();
        matching = findMatching(bipartiteGraph);
        writeResponseTest(matching);
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        boolean[][] bipartiteGraph = readData();
        int[] matching = findMatching(bipartiteGraph);
        writeResponse(matching);
        out.close();
    }

    boolean[][] readDataTest1() throws IOException {
        int numLeft = 3;
        int numRight = 4;
        int[][] data = new int[][]{{1, 1, 0, 1}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (data[i][j] == 1);
        return adjMatrix;
    }

    boolean[][] readDataTest2() throws IOException {
        int numLeft = 2;
        int numRight = 2;
        int[][] data = new int[][]{{1, 1}, {1, 0}};
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (data[i][j] == 1);
        return adjMatrix;
    }

    boolean[][] readData() throws IOException {
        int numLeft = in.nextInt();
        int numRight = in.nextInt();
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (in.nextInt() == 1);
        return adjMatrix;
    }

    private int[] findMatching(boolean[][] bipartiteGraph) {
        // Replace this code with an algorithm that finds the maximum
        // matching correctly in all cases.
        int numLeft = bipartiteGraph.length;
        int numRight = bipartiteGraph[0].length;

        int[] matching = new int[numLeft];
        Arrays.fill(matching, -1);

        FlowGraph fg = new FlowGraph(numRight + numLeft + 2);

        for (int i = 0; i < numLeft; i++) {
            fg.addEdge(0, i + 1, 1);
            for (int j = 0; j < numRight; j++) {
                if (bipartiteGraph[i][j]) {
                    fg.addEdge(i + 1, numLeft + j + 1, 1);
                }
            }
        }

        for (int i = 0; i < numRight; i++) {
            fg.addEdge(numLeft + i + 1, fg.size() - 1, 1);
        }

        FlowPath fp = new FlowPath();
        while (fp != null) {
            while (!fp.edges.isEmpty()) {
                Integer edgeNum = fp.edges.pop();
                fg.addFlow(edgeNum, fp.flow);
            }
            fp = getPath(fg);
        }
        for (int i = 0; i < numLeft; i++) {
            for (int edgeNum : fg.graph[i + 1]) {
                Edge e = fg.edges.get(edgeNum);
                if (e.flow > 0) {
                    matching[i] = e.to - (numLeft + 1);
                    break;
                }
            }
        }

//        boolean[] busyRight = new boolean[numRight];
//        for (int i = 0; i < numLeft; ++i)
//            for (int j = 0; j < numRight; ++j)
//                if (bipartiteGraph[i][j] && matching[i] == -1 && !busyRight[j]) {
//                    matching[i] = j;
//                    busyRight[j] = true;
//                }
        return matching;
    }

    private void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                out.print(" ");
            }
            if (matching[i] == -1) {
                out.print("-1");
            } else {
                out.print(matching[i] + 1);
            }
        }
        out.println();
    }

    private void writeResponseTest(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                System.out.print(" ");
            }
            if (matching[i] == -1) {
                System.out.print("-1");
            } else {
                System.out.print(matching[i] + 1);
            }
        }
    }


    private static FlowPath getPath(FlowGraph gr) {
        FlowPath fp = new FlowPath();
        PriorityQueue<Integer> q = new PriorityQueue<>();
        int s = gr.size() - 1;
        Integer[] prevEdge = new Integer[s + 1];
        q.add(0);
        while (!q.isEmpty()) {
            for (int edgeNum : gr.graph[q.poll()]) {
                Edge e = gr.getEdge(edgeNum);
                int cap = e.capacity - e.flow;
                int to = e.to;
                if (prevEdge[to] == null && cap > 0) {
                    q.add(to);
                    prevEdge[to] = edgeNum;
                    if (to == s) {
                        int backTrack = to;
                        fp.flow = Integer.MAX_VALUE;
                        do {
                            Integer peNum = prevEdge[backTrack];
                            Edge pe = gr.getEdge(peNum);
                            int availFlow = pe.capacity - pe.flow;
                            if (availFlow < fp.flow)
                                fp.flow = availFlow;
                            fp.edges.add(peNum);
                            backTrack = pe.from;
                        } while (backTrack > 0);
                        return fp;
                    }
                }
            }

        }

        return null;
    }

    // [tk] turn the stack into an arraylist? Or array since there are
    // only 3 possible steps?
    static class FlowPath {
        Stack<Integer> edges;
        int flow = 0;

        public FlowPath() {
            edges = new Stack<>();
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

    static class Edge {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
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
}
