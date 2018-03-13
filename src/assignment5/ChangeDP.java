package assignment5;

import java.util.Scanner;

public class ChangeDP {
    private static int getChange(int m) {
        //write your code here
        int[] coins = {1, 3, 4};
        int[] data = new int[m + 1];

        data[0] = 0;
        int coinsNum = 0;
        int coin = 0;
        for (int i = 1; i <= m; i++) {
            data[i] = Integer.MAX_VALUE;
            for (int c = 0; c < coins.length; c++) {
                coin = coins[c];
                if (i >= coin) {
                    coinsNum = data[i - coin] + 1;
                    if (coinsNum < data[i]) {
                        data[i] = coinsNum;
                    }
                }
            }
        }
        return data[data.length - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

//        test();
    }


    private static void test() {
        int money = 2;
        int result = getChange(money);
        System.out.println("money " + money + " result " + result);


        money = 34;
        result = getChange(money);
        System.out.println("money " + money + " result " + result);
    }
}

