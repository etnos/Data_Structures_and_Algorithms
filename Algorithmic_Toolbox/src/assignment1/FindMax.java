package assignment1;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class FindMax {

    public static void main(String[] args) {

        Random rand = new Random();
        int length = rand.nextInt(10);
        length = Math.max(2, length);

        int[] arr;
        IntStream ints = rand.ints(length, 2, 100000);
        arr = ints.toArray();


        System.out.println("length " + length);
        System.out.println(Arrays.toString(arr));
        System.out.println(findMaxRec(0, length - 1, arr));

        System.out.println(count);
    }

    static int count = 1;

    static int findMaxRec(int start, int end, int[] arr) {
        if (start >= end) {
            return arr[start];
        } else {
        count++;
            int mid = start + (end - start) / 2;
            int max1 = findMaxRec(start, mid, arr);
            int max2 = findMaxRec(1 + mid, end, arr);
            return Math.max(max1, max2);
        }
    }
}
