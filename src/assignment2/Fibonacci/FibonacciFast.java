package assignment2.Fibonacci;

import java.util.HashMap;

public class FibonacciFast {

    public static void main(String[] args) {

        long number = 79;


        for (int i = 0; i < 100; i++) {
            number = i;
            long resultFast = fibonacci(number, new HashMap<>());
            long resultNaive = Fibonacci.calc_fib(number);

            if (resultFast != resultNaive) {
                System.out.println("assignment2.Fibonacci resultFast " + number + "th is : " + resultFast);
                System.out.println("assignment2.Fibonacci resultNaive " + number + "th is : " + resultNaive);
                return;
            }
        }

    }

    private static long fibonacci(long n, HashMap<Long, Long> hashMap) {
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
            result = fibonacci(k, hashMap) * fibonacci(k, hashMap) + fibonacci(k - 1, hashMap) * fibonacci(k - 1, hashMap);
        } else {
            // even
            long k = n / 2;
            result = (2 * fibonacci(k - 1, hashMap) + fibonacci(k, hashMap)) * fibonacci(k, hashMap);
        }

        hashMap.put(n, result);
        return result;
    }
}
