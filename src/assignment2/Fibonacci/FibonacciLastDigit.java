package assignment2.Fibonacci;

import java.util.*;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigitNaive(int n) {
        if (n <= 1)
            return n;

        int prev2 = 0;
        int prev1 = 1;
        int result = 0;

        for (int i = 0; i < n - 1; ++i) {
            result = prev1 + prev2;
            if (result > 100) {
                result = result % 10;
            }
            prev2 = prev1;
            prev1 = result;
        }

        while (result >= 10) {
            result = result % 10;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigitNaive(n);
        System.out.println(c);
    }
}

