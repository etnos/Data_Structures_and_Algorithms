package assignment3;

import java.util.*;

public class DotProduct {
    private static long maxDotProduct(int[] a, int[] b) {
        //write your code here
        long result = 0;
        Arrays.sort(a);
        Arrays.sort(b);
        for (int i = 0; i < a.length; i++) {
            long al = a[i];
            long bl = b[i];
            result += al * bl;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));

//        hardcodedTest();
    }


    private static void hardcodedTest() {
        int[] a = {23};
        int[] b = {39};
        long result = 0;


        result = maxDotProduct(a, b);
        if (result != 897) {
            System.out.println("Wrong 897 " + result);
            return;
        }

        a = new int[]{1, 3, -5};
        b = new int[]{-2, 4, 1};
        result = maxDotProduct(a, b);
        if (result != 23) {
            System.out.println("Wrong 23 " + result);
            return;
        }
    }
}

