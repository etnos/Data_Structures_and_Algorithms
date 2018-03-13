package assignment2.Fibonacci;

// Java Program to find n'th fibonacci
// Number with O(Log n) arithmetic operations

class GFG {

    static int MAX = 1000;
    static long f[];

    // Returns n'th fibonacci number using
    // table f[]
    public static long fib(int n) {
        // Base cases
        if (n == 0)
            return 0;

        if (n == 1 || n == 2)
            return (f[n] = 1);

        // If fib(n) is already computed
        if (f[n] != 0)
            return f[n];

        if ((n & 1) == 1) {
            int k = (n + 1) / 2;
            // Applyting above formula [Note value
            // n&1 is 1 if n is odd, else 0.
            f[n] = (fib(k) * fib(k) +
                    fib(k - 1) * fib(k - 1));
        } else {
            int k = n / 2;
            // Applyting above formula [Note value
            // n&1 is 1 if n is odd, else 0.
            f[n] = (2 * fib(k - 1) + fib(k))
                    * fib(k);
        }


        return f[n];
    }

    /* Driver program to test above function */
    public static void main(String[] args) {
        int n = 79;
        f = new long[MAX];
        System.out.println(fib(n));
    }
}