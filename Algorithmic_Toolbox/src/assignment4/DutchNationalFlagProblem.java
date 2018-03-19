package assignment4;

import java.util.Arrays;

public class DutchNationalFlagProblem {

    public static void main(String[] args) {
        int n = 9;
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = (i + 1) % 3;
        }

        System.out.println(Arrays.toString(arr));
        dutchFlag(arr, 1);
        System.out.println(Arrays.toString(arr));

    }

    private static void dutchFlag(int[] a, int midValue) {
        System.out.println();
        int i = 0, j = 0, n = a.length - 1;
        while (j <= n) {
            if (a[j] < midValue) {
                // swap a[i] and a[j]
                swap(a, i, j);
                i++;
                j++;
            } else if (a[j] > midValue) {
                // swap a[j] and a[n]
                swap(a, j, n);
                n--;
            } else {
                j++;
            }
            System.out.println(Arrays.toString(a));
        }
        System.out.println();
    }

    private static void swap(int[] a, int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

}
