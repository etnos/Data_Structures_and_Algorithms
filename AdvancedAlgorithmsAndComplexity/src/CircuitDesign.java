import java.io.*;
import java.util.*;

public class CircuitDesign {
    private final InputReader reader;
    private final OutputWriter writer;

    public CircuitDesign(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CircuitDesign(reader, writer).run();
        writer.writer.flush();
    }

    class Clause {
        int firstVar;
        int secondVar;
    }

    class TwoSatisfiability {
        int numVars;
        Clause[] clauses;

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }
        }

        boolean isSatisfiableNaive(int[] result) {
            // This solution tries all possible 2^n variable assignments.
            // It is too slow to pass the problem.
            // Implement a more efficient algorithm here.
            for (int mask = 0; mask < (1 << numVars); ++mask) {
                for (int i = 0; i < numVars; ++i) {
                    result[i] = (mask >> i) & 1;
                }

                boolean formulaIsSatisfied = true;

                for (Clause clause : clauses) {
                    boolean clauseIsSatisfied = false;
                    if ((result[Math.abs(clause.firstVar) - 1] == 1) == (clause.firstVar < 0)) {
                        clauseIsSatisfied = true;
                    }
                    if ((result[Math.abs(clause.secondVar) - 1] == 1) == (clause.secondVar < 0)) {
                        clauseIsSatisfied = true;
                    }
                    if (!clauseIsSatisfied) {
                        formulaIsSatisfied = false;
                        break;
                    }
                }

                if (formulaIsSatisfied) {
                    return true;
                }
            }
            return false;
        }

        boolean isSatisfiableFast(int[] result) {
            List<Integer>[] adj = constructImplicationGraph(clauses);
            List<Set<Integer>> stronglyConnectedComponentsList = findStronglyConnectedComponents(adj);
            boolean isSAT = isSAT(stronglyConnectedComponentsList);
            if (!isSAT) return isSAT;
            findTruthAssignment(stronglyConnectedComponentsList, result);
            return isSAT;
        }

        private List<Integer>[] constructImplicationGraph(Clause[] clauses) {
            List<Integer>[] adj = createGraphStructure(2 * numVars);

            // Constructing the edges
            for (Clause clause : clauses) {
                int u = index(clause.firstVar);
                int v = index(clause.secondVar);
                adj[not(u)].add(v);
                adj[not(v)].add(u);
            }

            return adj;
        }

        /**
         * @param x a literal
         * @return a vertex that represents the position of x in the adjacency
         * list.
         */
        private int index(int x) {
            int v;
            if (x > 0) {
                // because we start count from 0
                // but the data starts from 1
                // do -1 to switch to 0 base
                v = x - 1;
            } else {
                v = 2 * numVars + x;
            }
            return v;
        }

        private int not(int v) {
            return (2 * numVars - 1) - v;
        }


        /**
         * @param adj The adjacency list of a directed graph G=(V,E), with
         *            card(V) = n and card(E)=m
         * @return The strongly connected components of G
         * @precondition 1 <= n <= 10^4 0 <= m <= 10^4
         */
        public List<Set<Integer>> findStronglyConnectedComponents(List<Integer>[] adj) {
            List<Integer>[] adjTransposed = transpose(adj);
            List<Integer> post = dfs(adjTransposed);
            boolean[] visited = new boolean[adj.length];
            List<Set<Integer>> stronglyConnectedComponentsList = new ArrayList<>();
            for (int i = post.size() - 1; i >= 0; i--) {
                int v = post.get(i);
                Set<Integer> stronglyConnectedComponents = new HashSet<>();
                if (!visited[v]) {
                    exploreSCC(adj, visited, post, v, stronglyConnectedComponents);
                    stronglyConnectedComponentsList.add(stronglyConnectedComponents);
                }
            }
            return stronglyConnectedComponentsList;
        }

        /**
         * @param adj The adjacency list of a directed graph G=(V,E), with
         *            card(V) = n and card(E)=m
         * @return The adjacency list of the transposed graph G=(V,E^T)of G.
         * Thatis E^T := {(v,u) in V^2 | (u,v) in E}
         * @precondition 1 <= n <= 10^4 0 <= m <= 10^4
         */
        private List<Integer>[] transpose(List<Integer>[] adj) {
            List<Integer>[] adjTransposed = createGraphStructure(adj.length);

            for (int v = 0; v < adj.length; v++) {
                List<Integer> neighbors = adj[v];
                for (Integer neighbor : neighbors) {
                    adjTransposed[neighbor].add(v);
                }
            }

            return adjTransposed;
        }

        private List<Integer>[] createGraphStructure(int size) {
            List<Integer>[] graph = new ArrayList[size];
            for (int i = 0; i < size; i++) {
                graph[i] = new ArrayList<>();
            }
            return graph;
        }

        private List<Integer> dfs(List<Integer>[] adj) {
            int n = adj.length;
            boolean[] visited = new boolean[n];
            ArrayList<Integer> post = new ArrayList<>();

            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    exploreDFS(adj, visited, post, v);
                }
            }
            return post;
        }

        private void exploreDFS(List<Integer>[] adj, boolean[] visited,
                                List<Integer> post, int v) {
            // Any vertex is reachable from itself
            visited[v] = true;
            for (Integer w : adj[v]) {
                if (!visited[w]) {
                    exploreDFS(adj, visited, post, w);
                }
            }
            post.add(v);
        }

        private void exploreSCC(List<Integer>[] adj, boolean[] visited,
                                List<Integer> post, int v, Set<Integer> stronglyConnectedComponent) {
            // Any vertex is reachable from itself
            visited[v] = true;
            stronglyConnectedComponent.add(v);
            for (Integer w : adj[v]) {
                if (!visited[w]) {
                    exploreSCC(adj, visited, post, w, stronglyConnectedComponent);
                }
            }
            post.add(v);
        }

        private boolean isSAT(List<Set<Integer>> sccs) {
            // travers all existed strongly connected components
            for (Set<Integer> scc : sccs) {
                // if it contains connections like X -> !X
                // SAT is Unsatisfiable because (True -> !True) or (False -> !False) are not correct
                for (Integer v : scc) if (scc.contains(not(v))) return false;
            }
            return true;
        }

        private void findTruthAssignment(List<Set<Integer>> sccs, int[] result) {
            Arrays.fill(result, -1);
            for (Set<Integer> scc : sccs) {
                for (int v : scc) {
                    if (v < result.length && result[v] == -1)
                        result[v] = 0;
                    else if (v >= result.length && result[not(v)] == -1)
                        result[not(v)] = 1;
                }
            }
        }
    }

    public void run() {
        courseraRun();
//        test1();
//        writer.printf("\n");
//        test2();
    }

    private void test1() {
        int n = 3;
        int m = 3;

        int[][] data = new int[][]{{1, -3}, {-1, 2}, {-2, -3}};

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = data[i][0];
            twoSat.clauses[i].secondVar = data[i][1];
        }

        int result[] = new int[n];
        if (twoSat.isSatisfiableFast(result)) {
            writer.printf("SATISFIABLE\n");
            for (int i = 1; i <= n; ++i) {
                if (result[i - 1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
    }

    private void test2() {
        int n = 1;
        int m = 2;

        int[][] data = new int[][]{{1, 1}, {-1, -1}};

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = data[i][0];
            twoSat.clauses[i].secondVar = data[i][1];
        }

        int result[] = new int[n];
        if (twoSat.isSatisfiableFast(result)) {
            writer.printf("SATISFIABLE\n");
            for (int i = 1; i <= n; ++i) {
                if (result[i - 1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
    }

    private void courseraRun() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = reader.nextInt();
            twoSat.clauses[i].secondVar = reader.nextInt();
        }

        int result[] = new int[n];
        if (twoSat.isSatisfiableFast(result)) {
            writer.printf("SATISFIABLE\n");
            for (int i = 1; i <= n; ++i) {
                if (result[i - 1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
