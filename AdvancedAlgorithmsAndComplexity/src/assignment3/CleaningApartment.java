package assignment3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class CleaningApartment {
    private final InputReader reader;
    private final OutputWriter writer;

    public CleaningApartment(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CleaningApartment(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertHampathToSat {
        int numVertices;
        Edge[] edges;

        ConvertHampathToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() {
            //find where there is edge from vertex to vertex, and where isn't
            boolean[][] isEdge = new boolean[numVertices][numVertices];
            for (Edge edge : edges) {
                isEdge[edge.from - 1][edge.to - 1] = true;
                isEdge[edge.to - 1][edge.from - 1] = true;
            }
            //calculate
            int circuits = 0;
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (i != j && !isEdge[i][j]) {
                        //no edge between i+1 and j+1
                        for (int k = 1; k < numVertices; k++) {
                            circuits++;
                        }
                    }
                }
            }
            circuits += (numVertices - 1) * numVertices * numVertices;
            circuits += 2 * numVertices;
            writer.printf(circuits + " " + numVertices * numVertices + "\n");
            //(numVertices-1)*numVertices*numVertices

            for (int j = 1; j <= numVertices; j++) {
                for (int i = 1; i <= numVertices; i++) {
                    for (int k = i + 1; k <= numVertices; k++) {
                        writer.printf("-" + index(i, j) + " -" + index(k, j) + " 0\n"); //1 vertex on each place
                        writer.printf("-" + index(j, i) + " -" + index(j, k) + " 0\n"); //vertex once in full path
                    }
                }
            }
            //each vertex must be used
            //+numVertices
            for (int j = 1; j <= numVertices; j++) {
                for (int i = 1; i <= numVertices; i++) {
                    writer.printf(index(i, j) + " ");
                }
                writer.printf("0\n");
            }

            //each place in path must be set
            //+numVertuces
            for (int i = 1; i <= numVertices; i++) {
                for (int j = 1; j <= numVertices; j++) {
                    writer.printf(index(i, j) + " ");
                }
                writer.printf("0\n");
            }


            //need to calculate
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (i != j && !isEdge[i][j]) {
                        //no edge between i+1 and j+1
                        for (int k = 1; k < numVertices; k++) {
                            writer.printf("-" + index(i + 1, k) + " -" + index(j + 1, k + 1) + " 0\n");
                        }
                    }
                }
            }


        }


        int index(int i, int j) {
            // 1,1 ->1 2,1 -> 2 3,1 ->3
            // 1,2 ->(j-1)max+i
            return (j - 1) * numVertices + i;
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
            Integer[][] booleanIndices = new Integer[numVertices][numVertices];
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    booleanIndices[i][j] = i * numVertices + j + 1;
                }
                solution.addAll(exactlyOneOf(booleanIndices[i]));
            }
            solution.addAll(differentStagesOfPath(booleanIndices));
            solution.addAll(connectedNodesOnly(booleanIndices, edges));

            writer.printf("%d %d\n", solution.size(), booleanIndices.length * booleanIndices.length);
            for (ArrayList<Integer> clause : solution) {
                for (Integer b : clause) {
                    writer.printf("%d ", b);
                }
                writer.printf("0\n");
            }

        }

        ArrayList<ArrayList<Integer>> exactlyOneOf(Integer[] row) {
            ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
            resultList.add(new ArrayList<>(Arrays.asList(row)));

            ArrayList<Integer> tempArray;
            for (int i = 0; i < row.length - 1; i++) {
                for (int j = i + 1; j < row.length; j++) {
                    tempArray = new ArrayList<>();
                    tempArray.add(row[i] * -1);
                    tempArray.add(row[j] * -1);
                    resultList.add(tempArray);
                }
            }
            return resultList;
        }

        ArrayList<ArrayList<Integer>> differentStagesOfPath(Integer[][] booleanIndices) {
            ArrayList<ArrayList<Integer>> rtrn = new ArrayList<>();
            for (int i = 0; i < booleanIndices[0].length; i++) {
                ArrayList<Integer> tmp = new ArrayList<>();
                for (int k = 0; k < booleanIndices.length; k++) {
                    tmp.add(booleanIndices[k][i]);
                }
                rtrn.add(tmp);
            }
            for (int i = 0; i < booleanIndices[0].length; i++) {
                for (int j = 0; j < booleanIndices.length - 1; j++) {
                    for (int k = j + 1; k < booleanIndices.length; k++) {
                        ArrayList<Integer> tmp = new ArrayList<>();
                        tmp.add(booleanIndices[j][i] * -1);
                        tmp.add(booleanIndices[k][i] * -1);
                        rtrn.add(tmp);
                    }
                }
            }
            return rtrn;
        }

        ArrayList<ArrayList<Integer>> connectedNodesOnly(Integer[][] booleanVertices, Edge[] edges) {
            ArrayList<ArrayList<Integer>> connections = new ArrayList<>();
            ArrayList<ArrayList<Integer>> rtrn = new ArrayList<>();
            for (Integer[] booleanVertex : booleanVertices) {
                connections.add(new ArrayList<>());
            }
            for (Edge edge : edges) {
                connections.get(edge.from - 1).add(edge.to - 1);
                connections.get(edge.to - 1).add(edge.from - 1);
            }
            for (int n = 0; n < connections.size(); n++) {
                ArrayList<Integer> node = connections.get(n);
                for (int i = 0; i < booleanVertices.length; i++) {
                    if (!node.contains(i) && i != n) {
                        for (int j = 0; j < booleanVertices[n].length - 1; j++) {
                            ArrayList<Integer> tmp = new ArrayList<>();
                            tmp.add(booleanVertices[n][j] * -1);
                            tmp.add(booleanVertices[i][j + 1] * -1);
                            rtrn.add(tmp);
                        }
                    }
                }
            }
            return rtrn;
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
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
