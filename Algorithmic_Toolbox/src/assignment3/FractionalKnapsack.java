package assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, PriorityQueue<Item> queue) {
        double value = 0;
        //write your code here
        Item item;
        while (capacity > 0 && !queue.isEmpty()) {

            item = queue.poll();
            if (capacity >= item.weight) {
                capacity -= item.weight;
                value += item.value;
            } else {
                value += capacity * item.ration;
                break;
            }

        }

        return value;//round(value, 4);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
//        int[] values = new int[n];
//        int[] weights = new int[n];
        int v, w;
        Item item;
        Comparator<Item> comparator = new ItemComparator();
        PriorityQueue<Item> queue = new PriorityQueue<>(n, comparator);
        for (int i = 0; i < n; i++) {
            v = scanner.nextInt();
            w = scanner.nextInt();
            item = new Item(v, w);
            queue.add(item);
        }
        System.out.println(getOptimalValue(capacity, queue));

//        test();
//        hardcodedTest();
    }

    private static void hardcodedTest() {
        int n = 3;
        int capacity = 50;
        int[] v = {60, 100, 120};
        int[] w = {20, 50, 30};

        double result = getOptimalValueSlow(capacity, v, w, new HashSet<>());
        if (result != 180.0) {
            System.out.println("Wrong 180.0 " + result);
            return;
        }

        n = 1;
        capacity = 10;
        v = new int[]{500};
        w = new int[]{30};

        result = getOptimalValueSlow(capacity, v, w, new HashSet<>());
        if (result != 166.6667) {
            System.out.println("Wrong 166.6667 " + result);
            return;
        }


        capacity = 7;
        v = new int[]{5, 8, 6, 5};
        w = new int[]{1, 5, 8, 3};
        Comparator<Item> comparator = new ItemComparator();
        PriorityQueue<Item> queue = new PriorityQueue<>(n, comparator);
        Item item;
        for (int i = 0; i < v.length; i++) {
            item = new Item(v[i], w[i]);
            queue.add(item);
        }

        result = getOptimalValueSlow(capacity, v, w, new HashSet<>());
        double resultFast = getOptimalValue(capacity, queue);
        if (result != resultFast) {
            System.out.println("Wrong " + result + " " + resultFast);
            return;
        }

        capacity = 7;
        v = new int[]{4, 5, 3, 6, 7, 1};
        w = new int[]{2, 1, 1, 6, 1, 1};
        queue = new PriorityQueue<>(n, comparator);
        for (int i = 0; i < v.length; i++) {
            item = new Item(v[i], w[i]);
            queue.add(item);
        }

        result = getOptimalValueSlow(capacity, v, w, new HashSet<>());
        resultFast = getOptimalValue(capacity, queue);
        if (result != resultFast) {
            System.out.println("Wrong " + result + " " + resultFast);
        }
    }

    private static void test() {
        Random rand = new Random();
        int vBound = 10; // (int) (2 * Math.pow(10, 6));
        int nBound = 10; //(int) (2 * Math.pow(10, 3));


        Comparator<Item> comparator = new ItemComparator();
        while (true) {

            int n = rand.nextInt(nBound);
            n = Math.max(1, n);
            int capacity = rand.nextInt(vBound);

            PriorityQueue<Item> queue = new PriorityQueue<>(n, comparator);
            PriorityQueue<Item> queue2 = new PriorityQueue<>(n, comparator);
            int[] value = new int[n];
            int[] weight = new int[n];
            int v, w;
            Item item;
            Item item2;

            for (int i = 0; i < n; i++) {
                v = rand.nextInt(vBound);
                w = rand.nextInt(vBound);
                w = Math.max(1, w);
                item = new Item(v, w);
                queue.add(item);

                item2 = new Item(v, w);
                queue2.add(item2);

                value[i] = v;
                weight[i] = w;
            }

            double resultFast = getOptimalValue(capacity, queue);
            resultFast = round(resultFast, 4);

            double resultSlow = getOptimalValueSlow(capacity, value, weight, new HashSet<>());
            resultSlow = round(resultSlow, 4);
            if (resultFast != resultSlow) {
                System.out.println("Wrong " + resultFast + " " + resultSlow);
                break;
            }
        }
        System.out.println("END");
    }

    private static double getOptimalValueSlow(int capacity, int[] value, int[] weight, HashSet<Integer> used) {
        double result = 0;
        while (capacity > 0 && used.size() != weight.length) {
            int nextIndex = getNextItemIndex(value, weight, used);
            used.add(nextIndex);
            int itemWeight = weight[nextIndex];
            if (capacity >= itemWeight) {
                capacity -= itemWeight;
                result += value[nextIndex];
            } else {
                result += capacity * (double) value[nextIndex] / itemWeight;
                break;
            }
        }

        return round(result, 4);
    }

    private static int getNextItemIndex(int[] value, int[] weight, HashSet<Integer> used) {

        int maxIndex = 0;
        for (int i = 0; i < weight.length; i++) {
            if (!used.contains(i)) {
                maxIndex = i;
                break;
            }
        }

        double maxRatio = (double) value[maxIndex] / weight[maxIndex];
        for (int i = 1; i < weight.length; i++) {
            if (!used.contains(i)) {
                double ration = (double) value[i] / weight[i];
                if (ration > maxRatio) {
                    maxRatio = ration;
                    maxIndex = i;
                }
            }
        }

        return maxIndex;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    static class ItemComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return Double.compare(o2.ration, o1.ration);
        }
    }

    static class Item {
        int value;
        int weight;
        double ration;

        Item(int v, int w) {
            value = v;
            weight = w;
            ration = (double) v / w;
        }
    }
} 
