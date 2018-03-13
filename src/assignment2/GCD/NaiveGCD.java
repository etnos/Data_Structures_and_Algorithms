package assignment2.GCD;

public class NaiveGCD {


    public static void main(String[] args) {


        System.out.println(gcd(24, 18));
    }


    static int gcd(int a, int b) {
        int best = 0;
        for (int i = 1; i < Math.min(a, b); i++) {
            if (a % i == 0 && b % i == 0) {
                best = i;
            }
        }

        return best;
    }
}
