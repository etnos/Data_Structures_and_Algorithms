package assignment4;

import java.util.*;

public class Inversions {

    private static long getNumberOfInversions(int[] a, int[] temp, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left) {
            return numberOfInversions;
        }
        int mid = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, temp, left, mid);
        numberOfInversions += getNumberOfInversions(a, temp, mid + 1, right);

        //write your code here
        numberOfInversions += merge(a, temp, left, mid + 1, right);
        return numberOfInversions;
    }

    private static long merge(int[] arr, int[] temp, int left, int mid, int right) {

        int i, j, k;
        long inversions = 0;

        i = left; /* i is index for left subarray*/
        j = mid;  /* j is index for right subarray*/
        k = left; /* k is index for resultant merged subarray*/
        while ((i <= mid - 1) && (j <= right)) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];

                inversions = inversions + (mid - i);
            }
        }

      /* Copy the remaining elements of left subarray
       (if there are any) to temp*/
        while (i <= mid - 1)
            temp[k++] = arr[i++];

      /* Copy the remaining elements of right subarray
       (if there are any) to temp*/
        while (j <= right)
            temp[k++] = arr[j++];

        /*Copy back the merged elements to original array*/
        for (i = left; i <= right; i++)
            arr[i] = temp[i];

        return inversions;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] temp = new int[n];
        System.out.println(getNumberOfInversions(a, temp, 0, a.length - 1));

//        test();
//        generatedTest();
    }

    private static void test() {
        int[] arr = new int[]{2, 3, 9, 2, 9};
        int[] temp = new int[arr.length];
        System.out.println(getInvCountSlow(arr) == getNumberOfInversions(arr, temp, 0, arr.length - 1));

        arr = new int[]{2, 1, 3, 9, 2, 9, 1};
        temp = new int[arr.length];
        System.out.println(getInvCountSlow(arr) == getNumberOfInversions(arr, temp, 0, arr.length - 1));
    }

    private static void generatedTest() {
        Random rand = new Random();
        int n;
        long res1, res2;
        int[] arr, arr1;
        int[] temp;


        while (true) {
            n = rand.nextInt(100000);
            n = Math.max(1, n);
            temp = new int[n];
            arr = new int[n];
            arr1 = new int[n];


            for (int i = 0; i < n; i++) {
                arr[i] = rand.nextInt(100);
                arr1[i] = arr[i];
            }

            res1 = getInvCountSlow(arr);
            res2 = getNumberOfInversions(arr, temp, 0, n - 1);
            if (res1 != res2) {
                System.out.println("Wrong res1 " + res1 + " res2 " + res2 + " array " + Arrays.toString(arr1));
                break;
            } else {
                System.out.println("res " + res1);
            }


        }

    }

    static int getInvCountSlow(int[] arr) {
        int n = arr.length;
        int inv_count = 0;
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (arr[i] > arr[j])
                    inv_count++;

        return inv_count;
    }


}

