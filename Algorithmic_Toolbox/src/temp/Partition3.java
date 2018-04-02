package temp;

import java.util.*;
import java.io.*;

public class Partition3 {
    // TODO does not work properly
    private static int partition3(int[] givenItems) {
        int givenItemsSum = 0;
        int totalItemsAmount = givenItems.length;

        // calculate the total sum of given items
        for (int a : givenItems) {
            givenItemsSum += a;
        }

        // if it is not dividable by 3, return 0
        if (givenItemsSum % 3 != 0 || givenItems.length < 3) {
            return 0;
        }

        int partition = givenItemsSum / 3;
        boolean[][][] array = new boolean[partition + 1][partition + 1][totalItemsAmount + 1];

        // initialize top row as true
//        for (int j = 0; j <= partition; j++) {
        for (int k = 0; k <= totalItemsAmount; k++) {
            array[0][0][k] = true;
        }
//        }

        // initialize leftmost column, except part[0][0], as 0
//        for (int i = 1; i <= partition; i++)
//            array[i][0][0] = false;

        boolean cell1, cell2, cell3;
        int itemValue;
        for (int i = 1; i <= partition; i++) {
            for (int j = 1; j <= partition; j++) {
                for (int k = 1; k <= totalItemsAmount; k++) {
//                    array[i][j][k] = array[i][j - 1][k] || array[i][j][k - 1];
                    array[i][j][k] = array[i - 1][j - 1][k - 1];
                    itemValue = givenItems[k - 1];
                    cell1 = false;
                    cell2 = false;
                    cell3 = false;
                    if (i >= itemValue) {
                        cell1 = array[i - itemValue][j - 1][k - 1];
                    }
                    if (j >= itemValue) {
                        cell2 = array[i - 1][j - itemValue][k - 1];
                    }
                    if (k >= itemValue) {
                        cell3 = array[i - 1][j - 1][k - itemValue];
                    }

                    array[i][j][k] = array[i][j][k] || cell1 || cell2 || cell3;


                }
            }
        }

        return array[partition][partition][totalItemsAmount] ? 1 : 0;
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
        // by default it is faulse
//        for (int i = 1; i <= sum / 2; i++)
//            array[i][0] = false;

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

        arr = new int[]{3, 3, 3, 3, 3};
        result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{3, 3, 3, 3, 3, 3};
        result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{17, 47, 59, 34, 57, 17, 23, 67, 47, 1, 18, 2, 59, 47
        };
        result = partition3(arr);
        System.out.println("result " + result);
    }
}

