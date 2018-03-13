package assignment5;

import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        while (n >= 1) {
            sequence.add(n);
            if (n % 3 == 0) {
                n /= 3;
            } else if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = myOptimalSequenceDynamic(n);
        System.out.println(sequence.size() - 1);
        for (int i = 1; i < sequence.size(); i++) {
            System.out.print(sequence.get(i) + " ");
        }

//        test();
    }


    private static List<Integer> myOptimalSequenceDynamic(int n) {
        int[] data = new int[n + 1];
        for (int i = 1; i < data.length; i++) {
            data[i] = data[i - 1] + 1;
            if (i % 2 == 0) data[i] = Math.min(1 + data[i / 2], data[i]);
            if (i % 3 == 0) data[i] = Math.min(1 + data[i / 3], data[i]);

        }

        List<Integer> sequence = new ArrayList<>();
        for (int i = n; i > 1; ) {
            sequence.add(i);
            if (data[i - 1] == data[i] - 1)
                i = i - 1;
            else if (i % 2 == 0 && (data[i / 2] == data[i] - 1))
                i = i / 2;
            else if (i % 3 == 0 && (data[i / 3] == data[i] - 1))
                i = i / 3;
        }
        sequence.add(1);

        Collections.reverse(sequence);
        return sequence;
    }

    private static void test() {
        int number = 5;

        List<Integer> sequence = myOptimalSequenceDynamic(number);

        System.out.println("Number: " + number);
        System.out.println(sequence.size() - 1);
        for (int i = 1; i < sequence.size(); i++) {
            System.out.print(sequence.get(i) + " ");
        }
        System.out.println();

        number = 1;

        sequence = myOptimalSequenceDynamic(number);
        System.out.println("Number: " + number);
        System.out.println(sequence.size() - 1);
        for (int i = 1; i < sequence.size(); i++) {
            System.out.print(sequence.get(i) + " ");
        }
        System.out.println();

        number = 96234;

        sequence = myOptimalSequenceDynamic(number);
        System.out.println("Number: " + number);
        System.out.println(sequence.size() - 1);
        for (int i = 1; i < sequence.size(); i++) {
            System.out.print(sequence.get(i) + " ");
        }
    }
}

