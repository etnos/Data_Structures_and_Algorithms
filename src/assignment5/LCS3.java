package assignment5;

import java.util.*;

public class LCS3 {

    private static int lcs3(int[] a, int[] b, int[] c) {
        //Write your code here

        if (a.length == 0 || b.length == 0 || c.length == 0) {
            return 0;
        }

        int[][][] matrix = new int[a.length + 1][b.length + 1][c.length + 1];

        //1- If any of the string is empty then there
        //   is no common subsequence at all then
        //           L[i][j][k] = 0
        //
        //2- If the characters of all sequences match
        //   (or X[i] == Y[j] ==Z[k]) then
        //        L[i][j][k] = 1 + L[i-1][j-1][k-1]
        //
        //3- If the characters of both sequences do
        //   not match (or X[i] != Y[j] || X[i] != Z[k]
        //   || Y[j] !=Z[k]) then
        //        L[i][j][k] = max(L[i-1][j][k],
        //                         L[i][j-1][k],
        //                         L[i][j][k-1])

        int cell1, cell2, cell3;
        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length; j++) {
                for (int k = 1; k <= c.length; k++) {
                    if (a[i - 1] == b[j - 1] && a[i - 1] == c[k - 1]) {
                        cell1 = matrix[i - 1][j - 1][k - 1];
                        matrix[i][j][k] = 1 + cell1;
                    } else {
                        cell1 = matrix[i - 1][j][k];
                        cell2 = matrix[i][j - 1][k];
                        cell3 = matrix[i][j][k - 1];
                        matrix[i][j][k] = Math.max(cell1, Math.max(cell2, cell3));
                    }
                }
            }

        }

        return matrix[a.length][b.length][c.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));

//        test();
    }


    private static void test() {
        int[] a = {1, 2, 3};
        int[] b = {2, 1, 3};
        int[] c = {1, 3, 5};
        System.out.println(lcs3(a, b, c));

        a = new int[]{8, 3, 2, 1, 7};
        b = new int[]{8, 2, 1, 3, 8, 10, 7};
        c = new int[]{6, 8, 3, 1, 4, 7};
        System.out.println(lcs3(a, b, c));
    }
}

