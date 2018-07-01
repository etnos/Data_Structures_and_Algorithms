package assignment3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class GSMNetwork {
    private final InputReader reader;
    private final OutputWriter writer;

    public GSMNetwork(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new GSMNetwork(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertGSMNetworkProblemToSat {
        int numVertices;
        Edge[] edges;

        ConvertGSMNetworkProblemToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() {
            int n = numVertices;
            int m = edges.length;
            int numOfVariables = 3 * n;
            int numOfClauses = n + 3 * m;
            writer.printf("%d %d\n", numOfClauses, numOfVariables);

            for (int i = 1; i <= n; i++) {
                writer.printf("%d %d %d 0\n", i, n + i, 2 * n + i);
            }

            for (Edge edge : edges) {
                int i = edge.from;
                int j = edge.to;
                writer.printf("%d %d 0\n", -i, -j);
                writer.printf("%d %d 0\n", -(i + n), -(j + n));
                writer.printf("%d %d 0\n", -(i + 2 * n), -(j + 2 * n));
            }
        }

        // *** SLOW!!!
        void printEquisatisfiableSatFormula2() {
            // This solution prints a simple satisfiable formula
            // and passes about half of the tests.
            // Change this function to solve the problem.
//            writer.printf("3 2\n");
//            writer.printf("1 2 0\n");
//            writer.printf("-1 -2 0\n");
//            writer.printf("1 -2 0\n");

            ArrayList<ArrayList<Integer>> solution = new ArrayList<>();
            Integer[][] booleanIndices = new Integer[numVertices][3];
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < 3; j++) {
                    booleanIndices[i][j] = i * 3 + j + 1;
                }
                solution.addAll(exactlyOneOf(booleanIndices[i]));
            }
            for (Edge edge : edges) {
                solution.addAll(
                        connectedNodes(
                                booleanIndices[edge.from - 1],
                                booleanIndices[edge.to - 1]
                        )
                );
            }
            writer.printf("%d %d\n", solution.size(), booleanIndices.length * 3);
            for (ArrayList<Integer> clause : solution) {
                for (Integer b : clause) {
                    writer.printf("%d ", b);
                }
                writer.printf("0\n");
            }

        }

        ArrayList<ArrayList<Integer>> exactlyOneOf(Integer[] row) {
            if (row.length > 3) {
                System.out.println("Wrong size row");
                return new ArrayList<>();
            }

            ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
            resultList.add(new ArrayList<>(Arrays.asList(row)));

            for (int i = 0; i < 3; i++) {
                //this only works with groups 3 or less!
                ArrayList<Integer> tempArray = new ArrayList<>();
                tempArray.add(row[i] * -1);
                tempArray.add(row[(i + 1) % row.length] * -1);
                resultList.add(tempArray);
            }
            return resultList;
        }

        ArrayList<ArrayList<Integer>> connectedNodes(Integer[] nodeA, Integer[] nodeB) {
            if (nodeA.length != nodeB.length) {
                return new ArrayList<>();
            }
            ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
            for (int i = 0; i < nodeA.length; i++) {
                ArrayList<Integer> tempList = new ArrayList<>();
                tempList.add(nodeA[i] * -1);
                tempList.add(nodeB[i] * -1);
                resultList.add(tempList);
            }
            return resultList;
        }
    }

    public void run() {
//        courseraRun();
        test1();
//
//        System.out.println(" ");
//        test2();
    }

    private void test1() {
        int n = 3;
        int m = 3;

        ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
        int[][] data = new int[][]{{1, 2}, {2, 3}, {1, 3}};
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = data[i][0];
            converter.edges[i].to = data[i][1];
        }

        converter.printEquisatisfiableSatFormula();
        writer.printf("\n\n");
        converter.printEquisatisfiableSatFormula2();
    }

    private void test2() {
        int n = 4;
        int m = 6;

        ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
        int[][] data = new int[][]{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}};
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = data[i][0];
            converter.edges[i].to = data[i][1];
        }

        converter.printEquisatisfiableSatFormula();
    }

    private void courseraRun() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }

        converter.printEquisatisfiableSatFormula();
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
