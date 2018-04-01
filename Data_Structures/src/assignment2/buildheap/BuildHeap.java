package assignment2.buildheap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
//        manualTest();
//        autoTest();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
        swaps = new ArrayList<Swap>();

        if (data.length == 0) {
            return;
        }

        for (int i = data.length - 1; i > 0; i--) {
            siftUp(i);
        }
    }

    private void siftUp(int j) {
        int parentIndex;
        int parent;
        while (j > 0) {
            parentIndex = (j - 1) / 2;
            parent = data[parentIndex];
            if (parent > data[j]) {
                swap(j, parentIndex);
                siftDown(j);
                j = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        int r, l;
        while (i * 2 + 1 < data.length) {
            if (data[i * 2 + 1] < data[i]) {
                swap(i * 2 + 1, i);
                siftDown(i * 2 + 1);
            } else if (i * 2 + 2 < data.length) {
                if (data[i * 2 + 2] < data[i]) {
                    swap(i * 2 + 2, i);
                    siftDown(i * 2 + 2);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

    }

    private void swap(int i, int j) {
        swaps.add(new Swap(j, i));
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }

        @Override
        public String toString() {
            return "index1 " + index1 + " index2 " + index2;
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


    private static void manualTest() {
        int n = 5;
        int[] arr = new int[]{5, 4, 3, 2, 1};
        BuildHeap heap = new BuildHeap();
        heap.data = arr;
        heap.generateSwaps();

        for (Swap swap : heap.swaps) {
            System.out.println(swap.index1 + " " + swap.index2);
        }
        System.out.println();
        System.out.println(Arrays.toString(heap.data));
        System.out.println();


        arr = new int[]{7, 6, 5, 4, 3, 2, 1,};
        heap = new BuildHeap();
        heap.data = arr;
        heap.generateSwaps();

        for (Swap swap : heap.swaps) {
            System.out.println(swap.index1 + " " + swap.index2);
        }
        System.out.println();
        System.out.println(Arrays.toString(heap.data));
        System.out.println();

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        heap = new BuildHeap();
        heap.data = arr;
        heap.generateSwaps();

        for (Swap swap : heap.swaps) {
            System.out.println(swap.index1 + " " + swap.index2);
        }
        System.out.println();
        System.out.println(Arrays.toString(heap.data));
        System.out.println();

        arr = new int[]{22, 25, 71, 24, 18, 5, 27, 32, 104, 8, 23, 66};
        heap = new BuildHeap();
        heap.data = arr;
        heap.generateSwaps();

        for (Swap swap : heap.swaps) {
            System.out.println(swap.index1 + " " + swap.index2);
        }
        System.out.println();
        System.out.println(Arrays.toString(heap.data));
        System.out.println();
    }

    private static void autoTest() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/assignment2/buildheap/tests/04")));
            String result = new String(Files.readAllBytes(Paths.get("src/assignment2/buildheap/tests/04.a")));

            StringTokenizer tok = new StringTokenizer(data);
            int n = Integer.parseInt(tok.nextToken());

            int[] array = new int[n];

            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(tok.nextToken());
            }


            tok = new StringTokenizer(result);
            n = Integer.parseInt(tok.nextToken());

            ArrayList<Swap> arrayResult = new ArrayList<Swap>(n);

            for (int i = 0; i < n; ) {
                arrayResult.add(new Swap(Integer.parseInt(tok.nextToken()), Integer.parseInt(tok.nextToken())));
                i += 2;
            }

            BuildHeap heap = new BuildHeap();
            heap.data = array;
            heap.generateSwaps();

            for (int i = 0; i < heap.swaps.size(); i++) {
                if (heap.swaps.get(i).index1 != arrayResult.get(i).index1 || heap.swaps.get(i).index2 != arrayResult.get(i).index2) {
                    System.out.println("WRONG " + i);
                    System.out.println("result " + heap.swaps.get(i));
                    System.out.println("expected " + arrayResult.get(i));
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
