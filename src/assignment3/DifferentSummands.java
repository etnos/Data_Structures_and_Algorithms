package assignment3;

import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<Integer>();
        //write your code here
        int count = 0;
        int lastNumber = 0;
        int temp = 0;
        while (count != n) {
            lastNumber++;
            temp = count + lastNumber;
            if (temp == n) {
                summands.add(lastNumber);
                break;
            } else {
                if (n - temp > lastNumber) {
                    summands.add(lastNumber);
                    count += lastNumber;
                } else {
                    summands.add(n - count);
                    break;
                }
            }
        }

        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }


//        hardcodedTest();
    }


    private static void hardcodedTest() {
        int n = 6;

        List<Integer> result = optimalSummands(n);
        if (result.size() != 3) {
            System.out.println("Wrong 6 size " + result.size() + " " + result);
            return;
        } else if (result.get(0) != 1 || result.get(1) != 2 || result.get(2) != 3) {
            System.out.println("Wrong 6 size " + result.size() + " " + result);
            return;
        }

        n = 8;
        result = optimalSummands(n);
        if (result.size() != 3) {
            System.out.println("Wrong 8 size " + result.size() + " " + result);
            return;
        } else if (result.get(0) != 1 || result.get(1) != 2 || result.get(2) != 5) {
            System.out.println("Wrong 8 size " + result.size() + " " + result);
            return;
        }


        n = 2;
        result = optimalSummands(n);
        if (result.size() != 1) {
            System.out.println("Wrong 2 size " + result.size() + " " + result);
            return;
        } else if (result.get(0) != 2) {
            System.out.println("Wrong 2 size " + result.size() + " " + result);
            return;
        }
    }
}

