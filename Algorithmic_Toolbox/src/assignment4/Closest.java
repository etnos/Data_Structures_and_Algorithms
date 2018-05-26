package assignment4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Closest {
    public static void main(String[] args) {
        mainCoursera();
//        test();
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
        double fast = minimalDistance(x, y);
        double naive = naiveMinimalDistance(x, y);
        if (fast != naive) {
            System.out.println("Wrong");
        }


        n = 4;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{7, 7}, {1, 100}, {4, 8}, {7, 7}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        fast = minimalDistance(x, y);
        naive = naiveMinimalDistance(x, y);
        if (fast != naive) {
            System.out.println("Wrong");
        }

        n = 4;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{0 ,0}, {5, 6}, {3, 4}, {7, 2}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        fast = minimalDistance(x, y);
        naive = naiveMinimalDistance(x, y);
        if (fast != naive) {
            System.out.println("Wrong");
        }



        n = 11;
        x = new int[n];
        y = new int[n];
        data = new int[][]{{4, 4}, {-2, -2}, {-3, -4}, {-1, 3}, {2, 3}, {-4, 0}, {1, 1}, {-1, -1}, {3, -1}, {-4, 2}, {-2, 4}};
        for (int i = 0; i < n; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        fast = minimalDistance(x, y);
        naive = naiveMinimalDistance(x, y);
        if (fast != naive) {
            System.out.println("Wrong");
        }
    }

    static class Point {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public double distanceTo(Point point) {
            return Math.sqrt((this.x - point.x) * (this.x - point.x) + (this.y - point.y) * (this.y - point.y));
        }

        public double deltaX(Point other) {
            return Math.abs(x - other.x);
        }
    }

    static double minimalDistance(int[] x, int y[]) {
        List<Point> list = getPointsList(x, y);
        return findMinimalDistance(list);
    }

    static double findMinimalDistance(List<Point> points) {
        points.sort(new XComparator());
        return solve(0, points.size() - 1, points);
    }

    private static double solve(int begin, int end, List<Point> pointList) {
        if (end - begin < 3) {
            return baseCase(begin, end, pointList);
        } else {
            return recursiveCase(begin, end, pointList);
        }
    }

    private static double baseCase(int begin, int end, List<Point> pointList) {
        double minimum = pointList.get(begin).distanceTo(pointList.get(end));
        if (end - begin > 1) {    // Three points
            minimum = Math.min(minimum, pointList.get(begin).distanceTo(pointList.get(begin + 1)));
            minimum = Math.min(minimum, pointList.get(begin + 1).distanceTo(pointList.get(end)));
        }
        return minimum;
    }

    private static double recursiveCase(int begin, int end, List<Point> pointList) {
        int middle = (begin + end) / 2;

        double leftDistance = solve(begin, middle, pointList);
        double rightDistance = solve(middle + 1, end, pointList);

        double minimum = Math.min(leftDistance, rightDistance);

        minimum = findMissedByRecursion(begin, middle, end, minimum, pointList);

        return minimum;
    }

    private static double findMissedByRecursion(int begin, int middle, int end, double minimum, List<Point> pointList) {
        Point middlePoint = pointList.get(middle);
        List<Point> suspects = new ArrayList<>();
        suspects.add(middlePoint);
        // check min X distance for all points before middle
        for (int i = begin; i < middle; i++) {
            Point point = pointList.get(i);
            if (point.deltaX(middlePoint) < minimum) {
                suspects.add(point);
            }
        }
        // check min X distance for all points after middle
        for (int i = middle + 1; i < end; i++) {
            Point point = pointList.get(i);
            if (middlePoint.deltaX(point) < minimum) {
                suspects.add(point);
            }
        }

        if (suspects.size() > 1) {
            // if suspected points more than 1,
            // sort by Y and check manually.
            suspects.sort(new YComparator());
            for (int i = 0; i < suspects.size(); i++) {
                for (int j = i + 1; j < i + 16 && j < suspects.size(); j++) {
                    minimum = Math.min(minimum, suspects.get(i).distanceTo(suspects.get(j)));
                }
            }
        }
        return minimum;
    }

    private static double naiveMinimalDistance(int[] x, int y[]) {
        List<Point> list = getPointsList(x, y);
        return naiveFindMinimalDistance(list);
    }

    private static List<Point> getPointsList(int[] x, int[] y) {
        List<Point> list = new ArrayList<>(x.length);
        for (int i = 0; i < x.length; i++) {
            list.add(new Point(x[i], y[i]));
        }
        return list;
    }

    private static double naiveFindMinimalDistance(List<Point> list) {
        double minimum = Double.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                double distance = list.get(i).distanceTo(list.get(j));
                minimum = Math.min(minimum, distance);
            }
        }
        return minimum;
    }

    static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            if (a.x > b.x) {
                return 1;
            } else if (b.x > a.x) {
                return -1;
            }
            return 0;
        }

    }

    static class YComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            if (a.y > b.y) {
                return 1;
            } else if (b.y > a.y) {
                return -1;
            }
            return 0;
        }

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
}
