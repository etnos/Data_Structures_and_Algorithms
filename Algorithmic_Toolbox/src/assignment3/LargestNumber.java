package assignment3;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LargestNumber {
    private static String largestNumber(String[] a) {
        //write your code here

        Arrays.sort(a, new MaxNumberComparator());
        StringBuilder result = new StringBuilder();
        for (String number : a) {
            result.append(number);
        }
        return result.toString();

    }

    private static String largestNumberSlow(ArrayList<String> list) {
        StringBuilder result = new StringBuilder();
        int maxNumber;
        int i;
        while (!list.isEmpty()) {
            i = 1;
            maxNumber = 0;
            while (i < list.size()) {
                if (isGreater(list.get(i), list.get(maxNumber))) {
                    maxNumber = i;
                }
                i++;
            }

            result.append(list.get(maxNumber));
            list.remove(maxNumber);
        }

        return result.toString();
    }

    private static boolean isGreater(String currentItem, String maxNumber) {
//        char currentChar, maxChar;
//        int i1 = 0, i2 = 0;
//        while (i1 < currentItem.length() || i2 < maxNumber.length()) {
//            currentChar = currentItem.charAt(Math.min(i1, currentItem.length() - 1));
//            maxChar = maxNumber.charAt(Math.min(i2, maxNumber.length() - 1));
//            if (maxChar > currentChar) {
//                return false;
//            } else if (maxChar < currentChar) {
//                return true;
//            } else {
//                i1++;
//                i2++;
//            }
//        }
//        return false;


        String num1 = currentItem + maxNumber;
        String num2 = maxNumber + currentItem;
        int i1 = Integer.parseInt(num1);
        int i2 = Integer.parseInt(num2);

        return i1 > i2;
    }

    private static class MaxNumberComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            char c1, c2;
            int i1 = 0, i2 = 0;
            while (i1 < o1.length() && i2 < o2.length()) {
                c1 = o1.charAt(Math.min(i1, o1.length() - 1));
                c2 = o2.charAt(Math.min(i2, o2.length() - 1));
                if (c2 > c1) {
                    return 1;
                } else if (c2 < c1) {
                    return -1;
                } else {
                    i1++;
                    i2++;
                }
            }

//            if (i1 == i2) {
//                return 0;
//            } else if (i1 == o1.length()) {
//                return 1;
//            } else {
//                return -1;
//            }
return 0;
        }
    }


    private static void hardcodedTest() {
        String result;
        String[] arr = new String[]{"21", "2"};
        result = largestNumber(arr);
        if (!result.equals("221") && !result.equals(largestNumberSlow(new ArrayList<>(Arrays.asList(arr))))) {
            System.out.println("Wrong 221 " + result);
            return;
        }


        arr = new String[]{"9", "4", "6", "1", "9"};
        result = largestNumber(arr);
        if (!result.equals("99641") && !result.equals(largestNumberSlow(new ArrayList<>(Arrays.asList(arr))))) {
            System.out.println("Wrong 99641 " + result);
            return;
        }

        arr = new String[]{"23", "39", "92"};
        result = largestNumber(arr);
        if (!result.equals("923923") && !result.equals(largestNumberSlow(new ArrayList<>(Arrays.asList(arr))))) {
            System.out.println("Wrong 99641 " + result);
            return;
        }

        arr = new String[]{"910", "91"};
        result = largestNumber(arr);
        if (!result.equals("91910") && !result.equals(largestNumberSlow(new ArrayList<>(Arrays.asList(arr))))) {
            System.out.println("Wrong 91910 " + result);
            return;
        }

        //97, 96, 74, 66, 33, 23, 13, 100, 10, 100
        arr = new String[]{"910"};
        result = largestNumber(arr);
        if (!result.equals("910") && !result.equals(largestNumberSlow(new ArrayList<>(Arrays.asList(arr))))) {
            System.out.println("Wrong 910 " + result);
            return;
        }

        arr = new String[]{"97", "96", "100", "10", "100"};
        result = largestNumber(arr);
        String result2 = largestNumberSlow(new ArrayList<>(Arrays.asList(arr)));
        if (!result.equals("979610100100") && !result.equals(result2)) {
            System.out.println("Wrong 979610100100 " + result);
            return;
        }
    }

    private static void test() {
        while (true) {
            int n = 10;//ThreadLocalRandom.current().nextInt(1, 11);
            String[] arr = new String[n];

            for (int i = 0; i < n; i++) {
                arr[i] = String.valueOf(ThreadLocalRandom.current().nextInt(1, 101));
            }

            ArrayList<String> list = new ArrayList<>(Arrays.asList(arr));
            String resultFast = largestNumber(arr);
            String resultSlow = largestNumberSlow(list);

            if (!resultFast.equals(resultSlow)) {
                int k = 0;
                while (k < resultFast.length()) {
                    if (resultFast.charAt(k) != resultSlow.charAt(k)) {
                        System.out.println("new equal " + k + " " + resultFast.charAt(k) + " " + resultSlow.charAt(k));
                    }
                    k++;
                }
                System.out.println("Wrong, " + resultFast + " " + resultSlow);
                System.out.println(Arrays.toString(arr));
                return;
            }
        }
    }

    private static void test2() {
        while (true) {
            int n1 = ThreadLocalRandom.current().nextInt(1, 1001);
            int n2 = ThreadLocalRandom.current().nextInt(1, 1001);

            String s1 = n1 + "" + n2;
            String s2 = n2 + "" + n1;

            int max1 = Integer.parseInt(s1);
            int max2 = Integer.parseInt(s2);

            if (isGreater(s1, s2)) {
                if (max1 < max2) {
                    System.out.println("Wrong");
                    return;
                }
            } else {
                if (max2 < max1) {
                    System.out.println("Wrong");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        String[] a = new String[n];
//        for (int i = 0; i < n; i++) {
//            a[i] = scanner.next();
//        }
////        System.out.println(largestNumber(a));
//        ArrayList<String> list = new ArrayList<>(Arrays.asList(a));
//        System.out.println(largestNumberSlow(list));

        hardcodedTest();
//        test();
//        test2();
    }
}

