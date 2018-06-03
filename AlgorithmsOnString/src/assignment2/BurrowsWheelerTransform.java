package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {
        StringBuilder result = new StringBuilder();
        String[] matrix = new String[text.length()];

        // write your code here
        for (int i = 0; i < text.length(); i++) {
            result.append(text.substring(i)).append(text.substring(0, i));
            matrix[i] = result.toString();
            result = new StringBuilder();
        }

        Arrays.sort(matrix);

//        System.out.println(Arrays.toString(matrix));

        for (int i = 0; i < matrix.length; i++) {
            result.append(matrix[i].charAt(text.length() - 1));
        }

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
//        new assignment2.BurrowsWheelerTransform().test();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }

    private void test() {
        String text = "AA$";
        System.out.println(BWT(text));
        System.out.println();
        text = "ACACACAC$";
        System.out.println(BWT(text));
        System.out.println();
        text = "AGACATA$";
        System.out.println(BWT(text));
    }
}
