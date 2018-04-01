package assignment2.merging_tables;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();

//       MergingTables(null, null).test();
//       MergingTables(null, null).autoTest();
    }

    class Table {
        Table parent;
        int rank;
        int numberOfRows;

        Table(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }

        Table getParent() {
            // find super parent and compress path
            while (parent != parent.parent) {
                parent = parent.parent;
            }
            return parent;
        }
    }

    int maximumNumberOfRows = -1;

    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        Table realSource = source.getParent();
        if (realDestination == realSource) {
            return;
        }

        // merge two components here
        // use rank heuristic
        // update maximumNumberOfRows
        if (realDestination.rank > realSource.rank) {
            realSource.parent = realDestination;
            realDestination.numberOfRows += realSource.numberOfRows;
            maximumNumberOfRows = Math.max(maximumNumberOfRows, realDestination.numberOfRows);
        } else if (realSource.rank > realDestination.rank) {
            realDestination.parent = realSource;
            realSource.numberOfRows += realDestination.numberOfRows;
            maximumNumberOfRows = Math.max(maximumNumberOfRows, realSource.numberOfRows);
        } else {
            realSource.parent = realDestination;
            realDestination.numberOfRows += realSource.numberOfRows;
            maximumNumberOfRows = Math.max(maximumNumberOfRows, realDestination.numberOfRows);
            realDestination.rank++;
        }

    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        Table[] tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(tables[destination], tables[source]);
            writer.printf("%d\n", maximumNumberOfRows);
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

    private void test() {
        int n = 5;
        int m = 5;

        Table[] tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = 1;
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }

        merge(tables[2], tables[4]);
        System.out.println(maximumNumberOfRows);
        merge(tables[1], tables[3]);
        System.out.println(maximumNumberOfRows);
        merge(tables[0], tables[3]);
        System.out.println(maximumNumberOfRows);
        merge(tables[4], tables[3]);
        System.out.println(maximumNumberOfRows);
        merge(tables[4], tables[2]);
        System.out.println(maximumNumberOfRows);

        System.out.println();
        n = 6;
        m = 4;

        tables = new Table[n];
        int[] data = new int[]{10, 0, 5, 0, 3, 3};
        for (int i = 0; i < n; i++) {
            int numberOfRows = data[i];
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }

        merge(tables[5], tables[5]);
        System.out.println(maximumNumberOfRows);
        merge(tables[5], tables[4]);
        System.out.println(maximumNumberOfRows);
        merge(tables[4], tables[3]);
        System.out.println(maximumNumberOfRows);
        merge(tables[3], tables[2]);
        System.out.println(maximumNumberOfRows);
    }

    private void autoTest() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/assignment2/merging_tables/tests/116")));
            String result = new String(Files.readAllBytes(Paths.get("src/assignment2/merging_tables/tests/116.a")));

            testCase(data, result, "116");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testCase(String data, String result, String fileName) {
        StringTokenizer tok = new StringTokenizer(data);
        int numWorkers = Integer.parseInt(tok.nextToken());
        int n = Integer.parseInt(tok.nextToken());
        int m = Integer.parseInt(tok.nextToken());

        Table[] tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = Integer.parseInt(tok.nextToken());
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }

        int[] destinationArray = new int[m];
        int[] sourceArray = new int[m];
        for (int i = 0; i < m; i++) {
            destinationArray[i] = Integer.parseInt(tok.nextToken());
            sourceArray[i] = Integer.parseInt(tok.nextToken());
        }


        int[] resultsArray = new int[m];
        for (int i = 0; i < m; i++) {
            merge(tables[destinationArray[i]], tables[sourceArray[i]]);
            resultsArray[i] = maximumNumberOfRows;
        }

        int[] expectedResultsArray = new int[m];
        tok = new StringTokenizer(result);
        for (int i = 0; i < m; i++) {
            expectedResultsArray[i] = Integer.parseInt(tok.nextToken());
        }

        for (int i = 0; i < m; i++) {
            if (resultsArray[i] != expectedResultsArray[i]) {
                System.out.println("Wrong " + fileName);
                System.out.println(i + " " + resultsArray[i] + " " + expectedResultsArray[i]);
                break;
            }
        }
    }
}
