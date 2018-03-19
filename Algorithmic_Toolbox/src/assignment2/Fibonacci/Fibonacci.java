package assignment2.Fibonacci;

import java.util.Scanner;

public class Fibonacci {
    public static long calc_fib(long n) {
        if (n <= 1)
            return n;

        long prev1 = 1, prev2 = 0, result = 0;

        for (int i = 2; i <= n; i++) {
            result = prev1 + prev2;
            prev2 = prev1;
            prev1 = result;
        }

        return result;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = 79;//in.nextInt();

        System.out.println(calc_fib(n));
    }
}
