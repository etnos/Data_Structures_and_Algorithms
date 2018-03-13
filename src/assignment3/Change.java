package assignment3;

import java.util.Scanner;

public class Change {
    private static int getChange(int m) {
        //write your code here
        int[] coins = {10, 5, 1};
        int count = 0;

        for (int i = 0; i < coins.length; i++) {
            while (m > 0) {
                if (m - coins[i] >= 0) {
                    count++;
                    m = m - coins[i];
                } else {
                    break;
                }
            }

            if (m == 0) {
                break;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}

