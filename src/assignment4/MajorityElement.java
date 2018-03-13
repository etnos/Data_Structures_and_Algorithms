package assignment4;

import java.util.*;
import java.io.*;

public class MajorityElement {
    private static int getMajorityElement(int[] arr) {
        int result = getMajorityElementRec(arr, 0, arr.length - 1);

        if (result != -1) {
            int count = 1;
            for (int i = 0; i < arr.length; i++) {
                if (i != result && arr[i] == arr[result]) {
                    count++;
                    if (count > arr.length / 2) {
                        return result;
                    }
                }
            }
        }

        return -1;
    }

    private static int count(int[] arr, int left, int right, int k) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (arr[i] == arr[k]) {
                count++;
            }
        }

        return count;
    }

    private static int getMajorityElementRec(int[] a, int left, int right) {
        if (left == right) {
            return left;
        }
        int mid = left + (right - left) / 2;

        int resultLeft = getMajorityElementRec(a, left, mid);
        int resultRight = getMajorityElementRec(a, mid + 1, right);

        if (resultLeft == resultRight) {
            return resultLeft;
        } else if (resultLeft == -1) {
            return resultRight;
        } else if (resultRight == -1) {
            return resultLeft;
        } else {
            int cLeft = count(a, left, right, resultLeft);
            int cRight = count(a, left, right, resultRight);

            if (cLeft > cRight) {
                return resultLeft;
            }

            return resultRight;
        }

    }

    private static int getMajoritySlow(int[] arr) {
        int k = 0;
        int count;
        for (int i = 0; i < arr.length; i++) {
            k = i;
            count = 1;
            for (int j = 0; j < arr.length; j++) {
                if (j != k) {
                    if (arr[k] == arr[j]) {
                        count++;
                    }

                    if (count > arr.length / 2) {
                        return k;
                    }
                }
            }
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
        if (getMajorityElement(a) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

//        test();
    }


    private static void test() {
        int[] arr = {3, 3, 4, 2, 4, 4, 2, 4, 4};
        int result;
        int resultSlow;

        result = getMajorityElement(arr);
        resultSlow = getMajoritySlow(arr);
        if (arr[result] != arr[resultSlow]) {
            System.out.println("Wrong 4 " + arr[result]);
            return;
        }

        arr = new int[]{1, 2, 1, 3, 1};
        result = getMajorityElement(arr);
        resultSlow = getMajoritySlow(arr);
        if (arr[result] != arr[resultSlow]) {
            System.out.println("Wrong 1 " + result);
            return;
        }

        arr = new int[]{1, 2, 1, 3, 1, 4};
        result = getMajorityElement(arr);
        resultSlow = getMajoritySlow(arr);
        if (result != resultSlow) {
            System.out.println("Wrong -1 " + result);
            return;
        }

        arr = new int[]{1, 1, 2, 2, 2};
        result = getMajorityElement(arr);
        resultSlow = getMajoritySlow(arr);
        if (arr[result] != arr[resultSlow]) {
            System.out.println("Wrong 2 " + result);
            return;
        }

        arr = new int[]{1, 3, 2};
        result = getMajorityElement(arr);
        resultSlow = getMajoritySlow(arr);
        if (result != resultSlow) {
            System.out.println("Wrong -1 " + result);
            return;
        }

        arr = new int[]{2, 3, 9, 2, 2};
        result = getMajorityElement(arr);
        resultSlow = getMajoritySlow(arr);
        if (arr[result] != arr[resultSlow]) {
            System.out.println("Wrong 2 " + result);
            return;
        }

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

