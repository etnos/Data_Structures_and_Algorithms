package assignment2.Fibonacci;

import java.util.*;

public class FibonacciHuge {

    private static long getFibonacciHugeNaive(long n, long m) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
        }

        return current % m;
    }

    private static long getFibonacciHugeFast(long n, long m) {
        if (n <= 1)
            return n;

        long pisanoPeriodLength = getPisanoPeriodLength(m);
        long reminder = n % pisanoPeriodLength;

        return getPisanoValue(reminder, m);
    }

    private static long getPisanoValue(long remainder, long m) {

        if (remainder <= 1) {
            return remainder;
        }

        long prev1 = 1, prev2 = 0, current = prev1 + prev2;
        for (int i = 1; i < remainder; i++) {
            current = (prev1 + prev2) % m;
            prev2 = prev1;
            prev1 = current;
        }

        return current;
    }


    private static long getPisanoPeriodLength(long m) {
        long prev1 = 1, prev2 = 0, current;
        for (long i = 0; true; i++) {
            current = (prev1 + prev2) % m;
            prev2 = prev1;
            prev1 = current;
            if (prev2 == 0 && prev1 == 1) {
                return i + 1;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();

//        for (long j = 1; j <= 92; j++) {
//            for (int i = 2; i <= 92; i++) {
//                long naive = getFibonacciHugeNaive(j, i);
//                long fast = getFibonacciHugeFast(j, i);
//                if (naive != fast) {
//                    System.out.println("Wrong " + i + " " + j + " naive " + naive + " fast " + fast);
//                    return;
//                } else {
////                    System.out.println("OK " + i + " naive " + naive + " fast " + fast);
//                }
//            }
//        }


        System.out.println(getFibonacciHugeFast(n, m));

    }
}

