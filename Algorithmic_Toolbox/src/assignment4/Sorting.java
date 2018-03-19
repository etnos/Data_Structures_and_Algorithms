package assignment4;

import java.io.*;
import java.util.*;

public class Sorting {
    private static Random random = new Random();

    private static int[] partition3(int[] a, int l, int r) {
        //write your code here

        int x = a[l];

        int m1 = -1;
        int m2 = -1;

        dutchFlag(a, x, l, r);

        for (int i = l; i <= r; i++) {
            if (a[i] == x) {
                if (m1 == -1) {
                    m1 = i;
                }
                m2 = i;
            } else if (a[i] > x) {
                break;
            }
        }


        int[] m = {m1, m2};
        return m;
    }

    private static void dutchFlag(int[] a, int midValue, int l, int r) {
        int i = l, j = l, n = r;
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
        }
    }

    private static void swap(int[] a, int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    private static int partition2(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] <= x) {
                j++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[l];
        a[l] = a[j];
        a[j] = t;
        return j;
    }

    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
//        int m = partition2(a, l, r);
//        randomizedQuickSort(a, l, m - 1);
//        randomizedQuickSort(a, m + 1, r);

        //use partition3
        int[] mArr = partition3(a, l, r);
        randomizedQuickSort(a, l, mArr[0] - 1);
        randomizedQuickSort(a, mArr[1] + 1, r);
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }

//        test();
    }

    private static void test() {
        int[] a = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};

        randomizedQuickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));


        a = new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        randomizedQuickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
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

