package assignment5;

import java.util.*;

class EditDistance {
    public static int editDistance(String s, String t) {
        //write your code here
        if (s.equals(t)) {
            return 0;
        }

        int[][] matrix = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            matrix[i][0] = i;
        }

        for (int i = 0; i <= t.length(); i++) {
            matrix[0][i] = i;
        }


        //if x == y, then dp[i][j] == dp[i-1][j-1]
        //if x != y, and we insert y for word1, then dp[i][j] = dp[i][j-1] + 1
        //if x != y, and we delete x for word1, then dp[i][j] = dp[i-1][j] + 1
        //if x != y, and we replace x with y for word1, then dp[i][j] = dp[i-1][j-1] + 1
        //When x!=y, dp[i][j] is the min of the three situations.
        int cell1, cell2, cell3;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    cell1 = matrix[i - 1][j - 1];
                    cell2 = matrix[i][j - 1];
                    cell3 = matrix[i - 1][j];

                    matrix[i][j] = Math.min(cell1, Math.min(cell2, cell3)) + 1;
                }
            }
        }


        return matrix[s.length()][t.length()];
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(editDistance(s, t));

//        test();
    }


    private static void test() {
        String str1 = "short";
        String str2 = "ports";
        System.out.println(editDistance(str1, str2));

        str1 = "ab";
        str2 = "ab";
        System.out.println(editDistance(str1, str2));

        str1 = "editing";
        str2 = "distance";
        System.out.println(editDistance(str1, str2));
    }

}
