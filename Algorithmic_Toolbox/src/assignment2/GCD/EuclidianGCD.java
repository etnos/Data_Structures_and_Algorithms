package assignment2.GCD;

public class EuclidianGCD {

    public static void main(String[] args) {

       // System.out.println(gcd(54, 24));
        System.out.println(gcd(24, 54));
        //System.out.println(gcd(357, 234));
    }


    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }
}
