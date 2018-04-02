import java.util.Arrays;
import java.util.Scanner;

public class PointsAndSegments {

    private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        //write your code here
        Pair[] pairs = new Pair[starts.length + ends.length + points.length];
        int index = 0;
        for (int i = 0; i < starts.length; i++) {
            pairs[index++] = new Pair(starts[i], 'l', 0);
        }
        for (int i = 0; i < ends.length; i++) {
            pairs[index++] = new Pair(ends[i], 'r', 0);
        }

        for (int i = 0; i < points.length; i++) {
            pairs[index++] = new Pair(points[i], 'p', i);
        }
        Arrays.sort(pairs);

        int count = 0;
        for (int i = 0; i < pairs.length; i++) {
            switch (pairs[i].label) {
                case 'l': {
                    count++;
                    break;
                }
                case 'r': {
                    count--;
                    break;
                }
                case 'p': {
                    cnt[pairs[i].index] = count;
                    break;
                }
            }
        }

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

    static class Pair implements Comparable<Pair> {
        int coordinate;
        int index;
        char label;

        public Pair(int x, char c, int i) {
            coordinate = x;
            label = c;
            index = i;
        }

        @Override
        public int compareTo(Pair p) {
            if (coordinate == p.coordinate) {
                return label - p.label;
            } else {
                return coordinate - p.coordinate;
            }
        }

        @Override
        public String toString() {
            return coordinate + " " + label;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //use fastCountSegments
//        int[] cnt = naiveCountSegments(starts, ends, points);
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }

//        test();
    }


    private static void test() {
        int[] starts = new int[]{2, 0, 7};
        int[] ends = new int[]{3, 5, 10};
        int[] points = new int[]{1, 6, 11};

        if (!Arrays.equals(naiveCountSegments(starts, ends, points), fastCountSegments(starts, ends, points))) {
            System.out.println("WRONG");
        }

        starts = new int[]{-10};
        ends = new int[]{10};
        points = new int[]{-100, 100, 0};

        if (!Arrays.equals(naiveCountSegments(starts, ends, points), fastCountSegments(starts, ends, points))) {
            System.out.println("WRONG");
        }
    }


}

