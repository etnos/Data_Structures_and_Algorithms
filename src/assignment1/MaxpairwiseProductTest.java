package assignment1;

import java.io.*;
import java.util.Random;

public class MaxpairwiseProductTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String filename = "dump.txt";
//        int size = (int) Math.pow(10, 5) * 2;
//        long[] x = new long[size];
//        for (int i = 0; i < size; i++) {
//            x[i] = i + 1;
//        }
//        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
//        outputStream.writeObject(x);


//        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename));
//        int[] x1 = (int[]) inputStream.readObject();


//        System.out.println(Arrays.toString(x1));

        stressTest(5, 9);
    }


    public static void stressTest(int n, int m) {
        Random rand = new Random();
        MaxPairwiseReturn result1;
        MaxPairwiseReturn result2;
        while (true) {
            int length = rand.nextInt(n);
            length = Math.max(2, length);

            long[] array = new long[length];

            for (int i = 0; i < length; i++) {
                array[i] = rand.nextInt(m);
            }

//            result1 = MaxPairwiseProductNaive.getMaxPairwiseProduct(array);
//            result2 = MaxPairwiseProductFast.getMaxPairwiseProduct(array);
//
//            if (result1.result != result2.result) {
//                System.out.println("Wrong answer ");
//                System.out.println("res1 " + result1.max1 + " " + result1.max2 + " " + result1.result);
//                System.out.println("res2 " + result2.max1 + " " + result2.max2 + " " + result2.result);
//                System.out.println(Arrays.toString(array));
//                return;
//            }
        }
    }
}
