package assignment6;

import java.util.*;

public class Knapsack {
    static int optimalWeight(int W, int[] weightArray) {

        int[][] arr = new int[weightArray.length + 1][W + 1];
        int currentCellValue = 0;
        int currentWight = 0;
        int currentNewValue = 0;
        for (int i = 1; i <= weightArray.length; i++) {
            for (int w = 1; w <= W; w++) {
                currentNewValue = 0;
                currentCellValue = arr[i - 1][w];
                currentWight = weightArray[i - 1];
                if (currentWight <= w) {
                    currentNewValue = arr[i - 1][w - currentWight] + currentWight;
                }
                arr[i][w] = Math.max(currentCellValue, currentNewValue);
            }
        }
        return arr[weightArray.length][W];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));

//        test();
    }


    private static void test() {
        int W = 10;
        int[] w = {1, 4, 8};
        int result = optimalWeight(W, w);
        System.out.println(result);

        W = 10;
        w = new int[]{6, 3, 4, 2};
        result = optimalWeight(W, w);
        System.out.println(result);
    }
}

