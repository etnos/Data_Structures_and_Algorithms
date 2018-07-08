package temp2;

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

        ArrayList<Integer> graph[];
        ArrayList<Integer> graphRev[];
        boolean explored[];
        int[] component;
        int post[];
        int order[];
        int sccNum = 0;
        int p = 0;
        HashMap<Integer, Boolean> truthValue;

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }

            graph = new ArrayList[numVars * 2 + 1];
            for (int i = 0; i < numVars * 2 + 1; i++)
                graph[i] = (new ArrayList<>());
            graphRev = new ArrayList[numVars * 2 + 1];
            for (int i = 0; i < numVars * 2 + 1; i++)
                graphRev[i] = (new ArrayList<>());
            explored = new boolean[numVars * 2 + 1];
            component = new int[numVars * 2 + 1];
            post = new int[numVars * 2 + 1];
            order = new int[numVars * 2 + 1];
            truthValue = new HashMap<>();
        }

        boolean isSatisfiable(int[] result) {
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

            for (Clause c : clauses) {
                //[tk] check these before running
                if (c.firstVar > 0) {
                    if (c.secondVar > 0) {
                        graph[numVars + c.firstVar].add(c.secondVar);
                        graph[numVars + c.secondVar].add(c.firstVar);
                        graphRev[c.secondVar].add(numVars + c.firstVar);
                        graphRev[c.firstVar].add(numVars + c.secondVar);
                    } else {
                        graph[numVars + c.firstVar].add(numVars - c.secondVar);
                        graph[-c.secondVar].add(c.firstVar);
                        graphRev[numVars - c.secondVar].add(numVars + c.firstVar);
                        graphRev[c.firstVar].add(-c.secondVar);
                    }

                } else {
                    if (c.secondVar > 0) {
                        graph[-c.firstVar].add(c.secondVar);
                        graph[numVars + c.secondVar].add(numVars - c.firstVar);
                        graphRev[c.secondVar].add(-c.firstVar);
                        graphRev[numVars - c.firstVar].add(numVars + c.secondVar);
                    } else {
                        graph[-c.firstVar].add(numVars - c.secondVar);
                        graph[-c.secondVar].add(numVars - c.firstVar);
                        graphRev[numVars - c.secondVar].add(-c.firstVar);
                        graphRev[numVars - c.firstVar].add(-c.secondVar);
                    }

                }
            }

            int i;

            Arrays.fill(explored, false);
            for (i = 2 * numVars; i > 0; i--) {
                if (!explored[i])
                    reverseDepthFirstSearch(i);
                order[post[i]] = i;
            }

            Arrays.fill(explored, false);
            for (i = 2 * numVars; i > 0; i--) {
                if (!explored[order[i]]) {
                    p = order[i];
                    depthFirstSearch(order[i]);
                }
            }

            for (i = 2 * numVars; i > 0; i--) {
                int constNum = order[i];
                if (constNum > numVars) {
                    if (stronglyConnected(constNum, constNum - numVars))
                        return false;
                    if (!truthValue.containsKey(component[constNum])) {
                        truthValue.put(component[constNum], true);
                        truthValue.put(component[constNum - numVars], false);
                    }
                } else {
                    if (stronglyConnected(constNum, constNum + numVars))
                        return false;
                    if (!truthValue.containsKey(component[constNum])) {
                        truthValue.put(component[constNum], true);
                        truthValue.put(component[constNum + numVars], false);
                    }
                }
            }
            for (int j = 1; j <= numVars; j++) {
                result[j - 1] = truthValue.get(component[j]) ? j : -j;
            }

            return true;
        }


        private void reverseDepthFirstSearch(int i) {
            explored[i] = true;
            for (int nodeNum = 0; nodeNum < graphRev[i].size(); nodeNum++) {
                int node = graphRev[i].get(nodeNum);
                if (!explored[node])
                    reverseDepthFirstSearch(node);
            }
            sccNum++;
            post[i] = sccNum;

        }

        private void depthFirstSearch(int i) {
            explored[i] = true;
            component[i] = p;
            for (int nodeNum = 0; nodeNum < graph[i].size(); nodeNum++) {
                int node = graph[i].get(nodeNum);
                if (!explored[node])
                    depthFirstSearch(node);
            }
        }

        private boolean stronglyConnected(int firstNode, int secondNode) {
            return component[firstNode] == component[secondNode];
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = reader.nextInt();
            twoSat.clauses[i].secondVar = reader.nextInt();
        }

        int result[] = new int[n];
        if (twoSat.isSatisfiable(result)) {
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

//        test1();
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
