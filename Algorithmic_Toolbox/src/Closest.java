import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Closest {
    static double closestDistance = Double.POSITIVE_INFINITY;
    // we do not need this, but they could be useful later
    static private Point closestPoint1, closestPoint2;

    static class Point implements Comparable<Point> {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return o.y == y ? Long.signum(x - o.x) : Long.signum(y - o.y);
        }

        public double distanceTo(Point point) {
            return Math.sqrt((this.x - point.x) * (this.x - point.x) + (this.y - point.y) * (this.y - point.y));
        }
    }

    static double minimalDistance(int[] x, int y[]) {

        //write your code here
        int n = x.length;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(x[i], y[i]);
        }


        // sort by x-coordinate (breaking ties by y-coordinate)
        Point[] pointsByX = new Point[n];
        for (int i = 0; i < n; i++) {
            pointsByX[i] = points[i];
        }
        Arrays.sort(pointsByX);

        // check for coincident points
        for (int i = 0; i < n - 1; i++) {
            if (pointsByX[i].equals(pointsByX[i + 1])) {
                closestPoint1 = pointsByX[i];
                closestPoint2 = pointsByX[i + 1];
                return 0.0;
            }
        }

        // sort by y-coordinate (but not yet sorted)
        Point[] pointsByY = new Point[n];
        for (int i = 0; i < n; i++) {
            pointsByY[i] = pointsByX[i];
        }

        // auxiliary array
        Point[] aux = new Point[n];

        return closest(pointsByX, pointsByY, aux, 0, n - 1);
    }

    private static double closest(Point[] pointsByX, Point[] pointsByY, Point[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid + 1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        merge(pointsByY, aux, lo, mid, hi);

        // aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x - median.x) < delta)
                aux[m++] = pointsByY[i];
        }

        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < m; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i + 1; (j < m) && (aux[j].y - aux[i].y < delta); j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < closestDistance) {
                        closestDistance = delta;
                        closestPoint1 = aux[i];
                        closestPoint2 = aux[j];
                    }
                }
            }
        }
        return delta;
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) throws Exception {
        mainCoursera();
//        test();
    }

    private static void mainCoursera() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = nextInt();
            y[i] = nextInt();
        }
        System.out.println(minimalDistance(x, y));
        writer.close();
    }

    static BufferedReader reader;
    static PrintWriter writer;
    static StringTokenizer tok = new StringTokenizer("");


    static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    static int nextInt() {
        return Integer.parseInt(next());
    }

    private static void test() {
        int n = 2;
        int[] x = new int[n];
        int[] y = new int[n];
        int[][] data = new int[][]{{0, 0}, {3, 4}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        System.out.println(minimalDistance(x, y));


        n = 4;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{7, 7}, {1, 100}, {4, 8}, {7, 7}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        System.out.println(minimalDistance(x, y));

        n = 11;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{4, 4}, {-2, -2}, {-3, -4}, {-1, 3}, {2, 3}, {-4, 0}, {1, 1}, {-1, -1}, {3, -1}, {-4, 2}, {-2, 4}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        System.out.println(minimalDistance(x, y));
    }

}
