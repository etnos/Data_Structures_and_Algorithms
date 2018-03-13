package assignment2.Fibonacci;

import java.util.*;

public class FibonacciPartialSum {
    private static long getFibonacciPartialSumNaive(long from, long to) {
        long sum = 0;

        long current = 0;
        long next = 1;

        for (long i = 0; i <= to; ++i) {
            if (i >= from) {
                sum += current;
            }

            long new_current = next;
            next = next + current;
            current = new_current;
        }

        return sum % 10;
    }

    private static long getFibonacciPartialSumFast(long from, long to) {
        long fromSum = fibSumFast(from - 1);
        long toSum = fibSumFast(to);
        toSum += 10;

        return (toSum - fromSum) % 10;
    }

    private static long fibSumFast(long n) {
        if (n <= 0) {
            return 0;
        }
        return fibFast(n + 2, new HashMap<>()) - 1;
    }

    private static long fibFast(long n, HashMap<Long, Long> hashMap) {
        if (n == 0) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        if (hashMap.containsKey(n)) {
            return hashMap.get(n);
        }

        long result = 0;
        if ((n & 1) == 1) {
            // odd
            long k = (n + 1) / 2;
            result = fibFast(k, hashMap) * fibFast(k, hashMap) + fibFast(k - 1, hashMap) * fibFast(k - 1, hashMap);
        } else {
            // even
            long k = n / 2;
            result = (2 * fibFast(k - 1, hashMap) + fibFast(k, hashMap)) * fibFast(k, hashMap);
        }

        if (result > 10) {
            result = result % 10;
        }

        hashMap.put(n, result);

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
//        System.out.println(getFibonacciPartialSumNaive(from, to));
        System.out.println(getFibonacciPartialSumFast(from, to));

//        for (int i = 0; i < 60; i++) {
//            for (int j = i; j < 60; j++) {
//                long naive = getFibonacciPartialSumNaive(i, j);
//                long fast = getFibonacciPartialSumFast(i, j);
//
//                if (fast != naive) {
//                    System.out.println(i + " " + j + " " + fast + " " + naive);
//                    return;
//                }
//            }
//        }
    }
}

