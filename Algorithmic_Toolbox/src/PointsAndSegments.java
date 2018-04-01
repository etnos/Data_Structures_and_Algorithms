import java.util.Arrays;
import java.util.Scanner;

public class PointsAndSegments {

    private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        //write your code here
        return cnt;
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n, m;
//        n = scanner.nextInt();
//        m = scanner.nextInt();
//        int[] starts = new int[n];
//        int[] ends = new int[n];
//        int[] points = new int[m];
//        for (int i = 0; i < n; i++) {
//            starts[i] = scanner.nextInt();
//            ends[i] = scanner.nextInt();
//        }
//        for (int i = 0; i < m; i++) {
//            points[i] = scanner.nextInt();
//        }
//        //use fastCountSegments
//        int[] cnt = naiveCountSegments(starts, ends, points);
//        for (int x : cnt) {
//            System.out.print(x + " ");
//        }

        test();
    }


    private static void test() {
        int[] points = new int[]{1, 6, 11};
        int[] starts = new int[]{0, 7};
        int[] ends = new int[]{5, 10};

        System.out.println(Arrays.toString(naiveCountSegments(starts, ends, points)));
    }
}

