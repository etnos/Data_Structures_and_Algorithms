import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PlacingParentheses {
    private static int getMaximValue(String exp) {
        //write your code here
        char[] arr = exp.toCharArray();
        int n = arr.length / 2 + 1;
        int[] d = new int[n];
        char[] op = new char[n - 1];
        int j = 0;
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                d[j++] = arr[i] - '0';
            } else {
                op[k++] = arr[i];
            }
        }

        return parentheses(d, op);
    }


    private static int parentheses(int[] d, char[] op) {
        int n = d.length;
        int[][] m = new int[n][n];
        int[][] M = new int[n][n];

        for (int i = 0; i < n; i++) {
            m[i][i] = d[i];
            M[i][i] = d[i];
        }

        int j = 0;
        Pair pair;
        for (int s = 0; s < n - 1; s++) {
            for (int i = 0; i < n - s; i++) {
                j = i + s;
                pair = getMinAndMax(m, M, op, i, j);
                m[i][j] = pair.min;
                M[i][j] = pair.max;
            }
        }

        return M[1][n - 1];
    }


    private static Pair getMinAndMax(int[][] m, int[][] M, char[] op, int i, int j) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int a, b, c, d;
        for (int k = i; k <= j - 1; k++) {
            a = eval(M[i][k], M[k + 1][j], op[k]);
            b = eval(M[i][k], m[k + 1][j], op[k]);
            c = eval(m[i][k], M[k + 1][j], op[k]);
            d = eval(m[i][k], m[k + 1][j], op[k]);

            min = Math.min(min, Math.min(Math.min(a, b), Math.min(c, d)));
            max = Math.max(max, Math.max(Math.max(a, b), Math.max(c, d)));
        }

        return new Pair(max, min);
    }

    private static int eval(int a, int b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String exp = scanner.next();
//        System.out.println(getMaximValue(exp));
        test();
    }

    private static void test() {
        String exp = "5-8+7*4-8+9";
        //  TODO Does not work
        System.out.println(getMaximValue(exp));
    }

    static class Pair {

        Pair(int max, int min) {
            this.max = max;
            this.min = min;
        }

        int min;
        int max;
    }
}

