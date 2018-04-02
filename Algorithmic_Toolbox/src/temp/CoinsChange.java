package temp;

public class CoinsChange {

    public static void main(String[] args) {
        coinProblem(10, new int[]{1, 2, 3, 4, 5});
    }

    private static void coinProblem(int n, int[] coins) {

        int[][] data = new int[coins.length + 1][n + 1];

        // init zero element for all coins.
        // there is only 1 way to get 0 amount with any coins value.
        for (int i = 0; i <= coins.length; i++) {
            data[i][0] = 1;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= n; j++) {
                data[i][j] = data[i - 1][j];
                if (j >= coins[i - 1]) {
                    data[i][j] = data[i - 1][j] + data[i][j - coins[i - 1]];
                }
            }
        }

        System.out.println(data[coins.length][n]);
    }
}
