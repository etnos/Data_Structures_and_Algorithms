package assignment1;
import java.util.*;
import java.io.*;

public class MaxPairwiseProductFast {
    private static long getMaxPairwiseProduct(long[] numbers) {
        long result;
        long[] comp1 = findSecondMaxRec(0, numbers.length - 1, numbers);

        long firstMax = comp1[1];
        long secondMax = comp1[2];
        for (int i = 3; i < comp1.length; i++) {
            secondMax = Math.max(secondMax, comp1[i]);
        }

        result = firstMax * secondMax;
        return result;
    }


    private static long[] findSecondMaxRec(int start, int end, long[] arr) {
        long[] compared;
        if (start == end) {
            compared = new long[2];
            compared[0] = 2;
            compared[1] = arr[start];
        } else {
            int mid = start + (end - start) / 2;
            long[] compared1 = findSecondMaxRec(start, mid, arr);
            long[] compared2 = findSecondMaxRec(mid + 1, end, arr);

            if (compared1[1] > compared2[1]) {
                compared = copy(compared1, compared2[1]);
            } else {
                compared = copy(compared2, compared1[1]);
            }
        }

        return compared;
    }

    private static long[] copy(long[] arr, long compared) {
        int length = (int) (arr[0] + 1);
        long[] comparedArr = Arrays.copyOf(arr, length);
        comparedArr[0] = length;
        comparedArr[length - 1] = compared;
        return comparedArr;
    }


    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        long[] numbers = new long[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        System.out.println(getMaxPairwiseProduct(numbers));
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