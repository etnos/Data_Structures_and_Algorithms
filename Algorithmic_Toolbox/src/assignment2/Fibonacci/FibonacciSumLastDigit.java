package assignment2.Fibonacci;

import java.util.*;

public class FibonacciSumLastDigit {
    private static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;
        long sum = 1;

        for (long i = 2; i <= n; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
//            if (current > 100) {
//                current = current % 100;
//            }
            sum += current;
        }

        return sum;
    }


    private static long getFibonacciSumFast(long n) {
        long result = fibonacci(n + 2, new HashMap<>()) - 1;

        return result;
    }

    private static long fibonacci(long n, HashMap<Long, Long> hashMap) {
        if (n <= 1) {
            return n;
        }

        if (hashMap.containsKey(n)) {
            return hashMap.get(n);
        }

        long result = 0;
        if ((n & 1) == 1) {
            // odd
            long k = (n + 1) / 2;
            result = fibonacci(k, hashMap) * fibonacci(k, hashMap) + fibonacci(k - 1, hashMap) * fibonacci(k - 1, hashMap);
        } else {
            // even
            long k = n / 2;
            result = (2 * fibonacci(k - 1, hashMap) + fibonacci(k, hashMap)) * fibonacci(k, hashMap);
        }

        if (result > 100) {
            result = result % 100;
        }

        hashMap.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
//        long s = getFibonacciSumNaive(n);
        long s = getFibonacciSumFast(n);
        System.out.println(s % 10);


//        for (int i = 0; i < 100; i++) {
//            long lastNumberNaive = getFibonacciSumNaive(i);
//            long lastNumberFast = getFibonacciSumFast(i);
//
//            if (lastNumberNaive != lastNumberFast) {
//                System.out.println("Wrong number " + i + "  naive " + lastNumberNaive + " fast " + lastNumberFast);
//                return;
//            }
//        }

    }
}

