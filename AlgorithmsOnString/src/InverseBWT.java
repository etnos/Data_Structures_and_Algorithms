import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class InverseBWT {
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

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();
        // write your code here
        int length = bwt.length();
        char[][] array = new char[length][2];


        return result.toString();
    }

    String inverseBWTSlow(String bwt) {
        StringBuilder result = new StringBuilder();
        // write your code here
        int length = bwt.length();
        char[][] matrix = new char[length][length];
        // init matrix last column
        for (int i = 0; i < length; i++) {
            matrix[i][matrix.length - 1] = bwt.charAt(i);
        }

        String[] temp = new String[length];

        for (int j = 0; j < length - 1; j++) {
            for (int i = 0; i < length; i++) {
                temp[i] = getLine(matrix, i);
            }
            Arrays.sort(temp);
            initMatrix(matrix, temp);
        }

//        printMatirx(matrix);
        // replace $
        for (int i = 1; i < length; i++) {
            result.append(matrix[0][i]);
        }
        result.append(matrix[0][0]);
        return result.toString();
    }

    private void initMatrix(char[][] matrix, String[] temp) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length(); j++) {
                matrix[i][j] = temp[i].charAt(j);
            }
        }
    }

    private String getLine(char[][] matrix, int row) {
        int column = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(matrix[row][matrix.length - 1]);
        while (column < matrix.length - 1 && matrix[row][column] > 0) {
            sb.append(matrix[row][column]);
            column++;
        }
        return sb.toString();
    }

    private void printMatirx(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + "|");
            }
            System.out.println();
        }
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
//        new InverseBWT().test();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }

    private void test() {
        String bwtText = "AC$A";
        System.out.println(inverseBWT(bwtText));

        bwtText = "AGGGAA$";
        System.out.println(inverseBWT(bwtText));

    }
}
