import java.util.*;
import java.io.*;

public class Partition3 {
    // TODO does not work properly
    private static int partition3(int[] A) {
        int sum = 0;
        int n = A.length;
        for (int a : A) {
            sum += a;
        }
        if (sum % 3 != 0) {
            return 0;
        }

        int partition = sum / 3;
        boolean[][][] array = new boolean[partition + 1][partition + 1][n + 1];

        // initialize top row as true
        for (int i = 0; i <= n; i++) {
            array[0][0][i] = true;
        }

        // initialize leftmost column, except part[0][0], as 0
        for (int i = 1; i <= partition; i++)
            array[i][0][0] = false;

        boolean cell1, cell2, cell3;
        int currentValue;
        for (int i = 1; i <= partition; i++) {
            for (int j = 1; j <= partition; j++) {
                for (int k = 1; k <= n; k++) {
                    array[i][j][k] = array[i][j - 1][k] || array[i][j][k - 1];
                    currentValue = A[k - 1];
                    cell1 = false;
                    cell2 = false;
                    cell3 = false;
                    if (i >= currentValue) {
                        cell1 = array[i - currentValue][j - 1][k - 1];
                    }
                    if (j >= currentValue) {
                        cell2 = array[i - 1][j - currentValue][k - 1];
                    }
                    if (k >= currentValue) {
                        cell3 = array[i - 1][j - 1][k - currentValue];
                    }

                    array[i][j][k] = array[i][j][k] || cell1 || cell2 || cell3;


                }
            }
        }

        return array[partition][partition][n] ? 1 : 0;
    }

    private static int partition2(int[] A) {
        int sum = 0;
        int n = A.length;
        for (int a : A) {
            sum += a;
        }
        if (sum % 2 != 0) {
            return 0;
        }

        int partition = sum / 2;
        boolean[][] array = new boolean[partition + 1][n + 1];

        // initialize top row as true
        for (int i = 0; i <= n; i++)
            array[0][i] = true;

        // initialize leftmost column, except part[0][0], as 0
        for (int i = 1; i <= sum / 2; i++)
            array[i][0] = false;

        for (int i = 1; i <= partition; i++) {
            for (int j = 1; j <= n; j++) {
                array[i][j] = array[i][j - 1];
                if (i >= A[j - 1]) {
                    array[i][j] = array[i][j] || array[i - A[j - 1]][j - 1];
                }
            }
        }

        return array[partition][n] ? 1 : 0;
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int[] A = new int[n];
//        for (int i = 0; i < n; i++) {
//            A[i] = scanner.nextInt();
//        }
//        System.out.println(partition3(A));

        test();
    }

    private static void test() {
        int[] arr = new int[]{17, 59, 34, 57, 17, 23, 67, 1, 18, 2, 59};
        int result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{3, 3, 3, 3};
        result = partition3(arr);
        System.out.println("result " + result);
    }
}

