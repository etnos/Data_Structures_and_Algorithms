import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BinarySearch {

    static int binarySearch(int[] a, int x) {
        int left = 0, right = a.length - 1;
        //write your code here
        int mid;
        if (x >= a[left] && x <= a[right]) {
            while (left <= right) {
                mid = left + (right - left) / 2;
                if (a[mid] == x) {
                    return mid;
                } else if (a[mid] > x) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    private static void manualTest() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int result;
        int x;

        x = 3;
        result = binarySearch(arr, x);
        if (result != 2) {
            System.out.println("Wrong search " + x + " index " + result);
            return;
        }

        x = -1;
        result = binarySearch(arr, x);
        if (result != -1) {
            System.out.println("Wrong search " + x + " index " + result);
            return;
        }

        x = 10;
        result = binarySearch(arr, x);
        if (result != -1) {
            System.out.println("Wrong search " + x + " index " + result);
            return;
        }

        x = 9;
        result = binarySearch(arr, x);
        if (result != 8) {
            System.out.println("Wrong search " + x + " index " + result);
            return;
        }

        x = 1;
        result = binarySearch(arr, x);
        if (result != 0) {
            System.out.println("Wrong search " + x + " index " + result);
            return;
        }

        x = 5;
        result = binarySearch(arr, x);
        if (result != 4) {
            System.out.println("Wrong search " + x + " index " + result);
            return;
        }
    }

    private static void test() {
        int n = 100000;
        int x;
        int[] arr = new int[n];
        int resultBinary;
        int resultLinear;

        for (int i = 0; i < n; i++) {
            arr[i] = i+1;
        }

        while (true) {
            x = ThreadLocalRandom.current().nextInt(-100, 150000);

            resultBinary = binarySearch(arr, x);
            resultLinear = binarySearch(arr, x);

            if (resultBinary != resultLinear) {
                System.out.println("Wrong " + resultBinary + " " + resultLinear);
            }
        }
    }

    static int linearSearch(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
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
        for (int i = 0; i < m; i++) {
            //replace with the call to binarySearch when implemented
//            System.out.print(linearSearch(a, b[i]) + " ");
            System.out.print(binarySearch(a, b[i]) + " ");
        }

//        manualTest();
//        test();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
