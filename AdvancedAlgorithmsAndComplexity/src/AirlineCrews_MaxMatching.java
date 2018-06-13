import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

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
        boolean[] busyRight = new boolean[numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                if (bipartiteGraph[i][j] && matching[i] == -1 && !busyRight[j]) {
                    matching[i] = j;
                    busyRight[j] = true;
                }
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
