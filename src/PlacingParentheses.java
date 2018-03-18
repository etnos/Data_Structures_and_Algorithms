import java.util.Scanner;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
        //write your code here
        char[] arr = exp.toCharArray();
        int numbersAmount = (arr.length + 1) / 2;
        long[] numbersArray = new long[numbersAmount];
        // amount of operations is always 1 less than amount of numbers
        char[] operationsArray = new char[numbersAmount - 1];
        int j = 0;
        int k = 0;
        // fill the arrays:
        // even is number
        // od is operation
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                numbersArray[j++] = arr[i] - '0';
            } else {
                operationsArray[k++] = arr[i];
            }
        }

        return parentheses(numbersArray, operationsArray);
    }


    private static long parentheses(long[] numbersArray, char[] operationsArray) {
        int numbersAmount = numbersArray.length;
        long[][] minimumValues = new long[numbersAmount][numbersAmount];
        long[][] MaximumValues = new long[numbersAmount][numbersAmount];

        // init values matrix by diagonal
        for (int i = 0; i < numbersAmount; i++) {
            minimumValues[i][i] = numbersArray[i];
            MaximumValues[i][i] = numbersArray[i];
        }

        int j;
        Pair pair;
        // from the first number to the second form the end.
        // because for evaluation we will use the current i-th number and the next, i+1-th number
        long numbersIndexWithoutLast = numbersAmount - 1;
        for (int s = 0; s < numbersIndexWithoutLast; s++) {
            // calculating only by diagonal
            for (int i = 0; i < numbersIndexWithoutLast - s; i++) {
                j = i + s + 1;
                pair = calculateMinAndMaxValue(minimumValues, MaximumValues, operationsArray, i, j);
                minimumValues[i][j] = pair.min;
                MaximumValues[i][j] = pair.max;
            }
        }

        // return the last element from the first row
        return MaximumValues[0][numbersAmount - 1];
    }


    /**
     * We need to calculate both, the min and the max values.
     * Because the min value could be -100 and the max could be 3;
     * And in the next step the min could be -50 and the max could be 5
     * the max possible result between min * min = 500 and max * max = 15 is 500;
     */
    private static Pair calculateMinAndMaxValue(long[][] minimumValues, long[][] MaximumValues, char[] operationsArray, int i, int j) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long a, b, c, d;
        for (int k = i; k <= j - 1; k++) {
            a = eval(MaximumValues[i][k], MaximumValues[k + 1][j], operationsArray[k]);
            b = eval(MaximumValues[i][k], minimumValues[k + 1][j], operationsArray[k]);
            c = eval(minimumValues[i][k], MaximumValues[k + 1][j], operationsArray[k]);
            d = eval(minimumValues[i][k], minimumValues[k + 1][j], operationsArray[k]);

            min = Math.min(min, Math.min(Math.min(a, b), Math.min(c, d)));
            max = Math.max(max, Math.max(Math.max(a, b), Math.max(c, d)));
        }

        return new Pair(max, min);
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
//        test();
    }

    private static void test() {
        String exp = "5-8+7*4-8+9";
        System.out.println(getMaximValue(exp));
    }

    static class Pair {

        Pair(long max, long min) {
            this.max = max;
            this.min = min;
        }

        long min;
        long max;
    }
}

