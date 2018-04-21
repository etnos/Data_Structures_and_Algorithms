import java.io.*;
import java.util.*;

class RopeProblem {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    class Rope {
        String s;

        void process(int i, int j, int k) {
            // Replace this code with a faster implementation
            String t = s.substring(0, i) + s.substring(j + 1);
            s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
        }

        String result() {
            return s;
        }

        Rope(String s) {
            this.s = s;
        }
    }

    public static void main(String[] args) throws IOException {
//        new RopeProblem().run();
        new RopeProblem().test();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i, j, k);
        }
        out.println(rope.result());
        out.close();
    }

    private void test() {
        String s = "hlelowrold";
        int q = 2;
        int[] i = new int[]{1, 6};
        int[] j = new int[]{1, 6};
        int[] k = new int[]{2, 7};

        Rope rope = new Rope(s);
        for (int r = 0; r < q; r++) {
            rope.process(i[r], j[r], k[r]);
        }

        System.out.println("old s = " + s + " new s = " + rope.result());

        s = "abcdef";
        q = 2;
        i = new int[]{0, 4};
        j = new int[]{1, 5};
        k = new int[]{1, 0};

        rope = new Rope(s);
        for (int r = 0; r < q; r++) {
            rope.process(i[r], j[r], k[r]);
        }

        System.out.println("old s = " + s + " new s = " + rope.result());
    }
}
