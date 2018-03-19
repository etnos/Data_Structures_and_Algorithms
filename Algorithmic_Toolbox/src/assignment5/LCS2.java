package assignment5;

import java.util.*;

public class LCS2 {

    private static int lcs2(int[] a, int[] b) {
        //Write your code here
        int[][] matrix = new int[a.length + 1][b.length + 1];
        int cell1, cell2, cell3;
        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length; j++) {
                if (a[i - 1] == b[j - 1]) {
                    cell1 = matrix[i - 1][j - 1];
                    matrix[i][j] = cell1 + 1;
                } else {
                    cell2 = matrix[i][j - 1];
                    cell3 = matrix[i - 1][j];
                    matrix[i][j] = Math.max(cell2, cell3);
                }
            }
        }
        return matrix[a.length][b.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = scanner.nextInt();
        }

        System.out.println(lcs2(a, b));

//        test();
    }


    private static void test() {
        int[] arr1 = {2, 7, 8, 3};
        int[] arr2 = {5, 2, 8, 7};
        System.out.println(lcs2(arr1, arr2));

        arr1 = new int[]{7};
        arr2 = new int[]{1, 2, 3, 4};
        System.out.println(lcs2(arr1, arr2));

        arr1 = new int[]{2, 7, 5};
        arr2 = new int[]{2, 5};
        System.out.println(lcs2(arr1, arr2));

        arr1 = new int[]{1, 2, 1};
        arr2 = new int[]{2, 1, 2};
        System.out.println(lcs2(arr1, arr2));

        arr1 = new int[]{1, 2, 1};
        arr2 = new int[]{2, 1, 1};
        System.out.println(lcs2(arr1, arr2));

    }
}

