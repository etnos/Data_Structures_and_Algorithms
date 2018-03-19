package assignment1;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class FindMaxSecond {

    public static void main(String[] args) {
        Random rand = new Random();
        int length = rand.nextInt(10);
        length = Math.max(2, length);

        int[] arr;
        IntStream ints = rand.ints(length, 2, 100000);
        arr = ints.toArray();


        System.out.println("length " + length);
        System.out.println(Arrays.toString(arr));


        int[] comp1 = FindSecondMaxRec(0, length - 1, arr);
        System.out.println(Arrays.toString(comp1));

        System.out.println("Max1 " + comp1[1]);

        int max = comp1[2];
        for (int i = 3; i < comp1.length; i++) {
            max = Math.max(max, comp1[i]);
        }

        System.out.println("Max2 " + max);
    }


    public static int[] FindSecondMaxRec(int start, int end, int[] arr) {
        int[] compared;
        if (start == end) {
            compared = new int[2];
            compared[0] = 2;
            compared[1] = arr[start];
        } else {
            int mid = start + (end - start) / 2;
            int[] compared1 = FindSecondMaxRec(start, mid, arr);
            int[] compared2 = FindSecondMaxRec(mid + 1, end, arr);

            if (compared1[1] > compared2[1]) {
                compared = copy(compared1, compared2[1]);
            } else {
                compared = copy(compared2, compared1[1]);
            }
        }

        return compared;
    }

    static int[] copy(int[] arr, int compared) {
        int length = arr[0] + 1;
        int[] comparedArr = Arrays.copyOf(arr, length);
        comparedArr[0] = length;
        comparedArr[length - 1] = compared;
        return comparedArr;
    }

}
