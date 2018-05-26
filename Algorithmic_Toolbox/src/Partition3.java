import java.util.*;
import java.io.*;

public class Partition3 {
    private static int partition3(int[] givenItems) {
        //write your code here
        int givenItemsSum = 0;
        int totalItemsAmount = givenItems.length;

        // calculate the total sum of given items
        for (int a : givenItems) {
            givenItemsSum += a;
        }

        //  1. If dividing the sum of values by three I get a remainder,
        // or if there are less than three elements in the list, for sure there is no way of 3-partition my list.
        // if it is not dividable by 3, return 0
        if (givenItemsSum % 3 != 0 || givenItems.length < 3) {
            return 0;
        }

        int partition = givenItemsSum / 3;
        // 2. Build the table as discussed above. Note the zero as default value, even in the dummy top row - it is not
        // formally correct, but those values are not used anywhere.
        int[][] array = new int[partition + 1][totalItemsAmount + 1];

        for (int i = 1; i < partition + 1; i++) {
            // 3. Loop on all the "real" cells.
            for (int j = 1; j < totalItemsAmount + 1; j++) {
                // 4. Precalculate the row for the (b) check described above.
                int k = i - givenItems[j - 1];
                // 5. The first part of the condition is the (a) check above. If it fails, we pass to the second part,
                // using the row calculate in (4). If one of the two conditions is true, the value of the current
                // cell is increased up to 2.
                if (givenItems[j - 1] == i || k > 0 && array[k][j - 1] > 0) {
                    if (array[i][j - 1] == 0) {
                        array[i][j] = 1;
                    } else {
                        array[i][j] = 2;
                    }
                } else {
                    // Moving to the right we keep the value already calculate for the previous cell.
                    array[i][j] = array[i][j - 1];
                }
            }
        }

        return array[partition][totalItemsAmount] == 2 ? 1 : 0;
    }

    public static void main(String[] args) {
        courseraMain();
//        test();
    }

    private static void courseraMain() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        System.out.println(partition3(A));
    }


    private static void test() {
        int[] arr = new int[]{17, 59, 34, 57, 17, 23, 67, 1, 18, 2, 59};
        int result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{3, 3, 3, 3};
        result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{3, 3, 3, 3, 3};
        result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{3, 3, 3, 3, 3, 3};
        result = partition3(arr);
        System.out.println("result " + result);

        arr = new int[]{17, 47, 59, 34, 57, 17, 23, 67, 47, 1, 18, 2, 59, 47};
        result = partition3(arr);
        System.out.println("result " + result);
    }
}

