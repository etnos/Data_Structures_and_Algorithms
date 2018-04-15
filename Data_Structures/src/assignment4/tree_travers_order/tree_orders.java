package assignment4.tree_travers_order;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class tree_orders {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeOrders {
        int n;
        int[] key, left, right;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) {
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }

        List<Integer> inOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            // Finish the implementation
            // You may need to add a new recursive method to do that
//            inOrderRec(0, result);

            Stack<Integer> stack = new Stack<>();
            stack.push(0);
            int i;
            boolean isLeft = true;
            while (!stack.isEmpty()) {
                i = stack.peek();
                if (left[i] != -1 && isLeft) {
                    stack.push(left[i]);
                    continue;
                }
                stack.pop();
                result.add(key[i]);
                if (right[i] != -1) {
                    stack.push(right[i]);
                    isLeft = true;
                } else {
                    isLeft = false;
                }
            }


            return result;
        }

        // StackOverflowException - input data too big
        void inOrderRec(int i, ArrayList<Integer> result) {
            if (i == -1) {
                return;
            }

            inOrderRec(left[i], result);
            result.add(key[i]);
            inOrderRec(right[i], result);
        }

        List<Integer> preOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            // Finish the implementation
            // You may need to add a new recursive method to do that
//            preOrderRec(0, result);

            Stack<Integer> stack = new Stack<>();
            stack.push(0);
            int i;
            while (!stack.isEmpty()) {
                i = stack.pop();
                result.add(key[i]);

                if (right[i] != -1) {
                    stack.push(right[i]);
                }

                if (left[i] != -1) {
                    stack.push(left[i]);
                }


            }

            return result;
        }

        // StackOverflowException - input data too big
        void preOrderRec(int i, ArrayList<Integer> result) {
            if (i == -1) {
                return;
            }

            result.add(key[i]);
            preOrderRec(left[i], result);
            preOrderRec(right[i], result);
        }

        List<Integer> postOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            // Finish the implementation
            // You may need to add a new recursive method to do that
//            postOrderRec(0, result);

            Stack<Integer> stack = new Stack<>();
            stack.push(0);
            int i;
            int cLeft, cRight;
            while (!stack.isEmpty()) {
                i = stack.peek();
                cLeft = left[i];
                cRight = right[i];

                if (cRight == -1 && cLeft == -1) {
                    // leaf
                    stack.pop();
                    result.add(key[i]);
                } else if (cRight != -1 && cLeft != -1) {
                    //has both childes
                    stack.push(cRight);
                    right[i] = -1;

                    stack.push(cLeft);
                    left[i] = -1;
                } else {
                    if (cLeft != -1) {
                        // only left child exist
                        stack.push(cLeft);
                        left[i] = -1;
                    } else {
                        // only right child exist
                        stack.push(cRight);
                        right[i] = -1;
                    }
                }
            }

            return result;
        }

        // StackOverflowException - input data too big
        void postOrderRec(int i, ArrayList<Integer> result) {
            if (i == -1) {
                return;
            }

            postOrderRec(left[i], result);
            postOrderRec(right[i], result);
            result.add(key[i]);
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_orders().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();

//        assignment4.tree_travers_order.tree_orders to = new assignment4.tree_travers_order.tree_orders();
//        to.test();
//        to.autoTest();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        TreeOrders tree = new TreeOrders();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }

    private void test() {
        TreeOrders tree = new TreeOrders();
        tree.n = 5;
        tree.key = new int[]{4, 2, 5, 1, 3};
        tree.left = new int[]{1, 3, -1, -1, -1};
        tree.right = new int[]{2, 4, -1, -1, -1};


        Integer[] resInOrder = new Integer[]{1, 2, 3, 4, 5};
        Integer[] resPreOrder = new Integer[]{4, 2, 1, 3, 5};
        Integer[] resPostOrder = new Integer[]{1, 3, 2, 5, 4};

        Integer[] resultArray = new Integer[tree.n];
        resultArray = tree.inOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resInOrder)) {
            System.out.println("Wrong 1 inOrder");
        }

        resultArray = new Integer[tree.n];
        resultArray = tree.preOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resPreOrder)) {
            System.out.println("Wrong 1 preOrder");
        }

        resultArray = new Integer[tree.n];
        resultArray = tree.postOrder().toArray(resultArray);
        if (!Arrays.equals(resultArray, resPostOrder)) {
            System.out.println("Wrong 1 postOrder");
        }


        System.out.println();

        tree = new TreeOrders();
        tree.n = 10;
        tree.key = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80, 90};
        tree.left = new int[]{7, -1, -1, 8, 3, -1, 1, 5, -1, -1};
        tree.right = new int[]{2, -1, 6, 9, -1, -1, -1, 4, -1, -1};

        resInOrder = new Integer[]{50, 70, 80, 30, 90, 40, 0, 20, 10, 60};
        resPreOrder = new Integer[]{0, 70, 50, 40, 30, 80, 90, 20, 60, 10};
        resPostOrder = new Integer[]{50, 80, 90, 30, 40, 70, 10, 60, 20, 0};

        resultArray = new Integer[tree.n];
        resultArray = tree.inOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resInOrder)) {
            System.out.println("Wrong 2 inOrder");
        }

        resultArray = new Integer[tree.n];
        resultArray = tree.preOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resPreOrder)) {
            System.out.println("Wrong 2 preOrder");
        }

        resultArray = new Integer[tree.n];
        resultArray = tree.postOrder().toArray(resultArray);
        if (!Arrays.equals(resultArray, resPostOrder)) {
            System.out.println("Wrong 2 postOrder");
            System.out.println(Arrays.toString(resultArray));
        }
    }

    private void autoTest() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/assignment4/tree_travers_order/tests/21")));
            String result = new String(Files.readAllBytes(Paths.get("src/assignment4/tree_travers_order/tests/21.a")));

            testCase(data, result, "02");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void testCase(String data, String result, String fileName) {
        StringTokenizer tok = new StringTokenizer(data);
        TreeOrders tree = new TreeOrders();
        tree.n = Integer.parseInt(tok.nextToken());
        tree.key = new int[tree.n];
        tree.left = new int[tree.n];
        tree.right = new int[tree.n];

        for (int i = 0; i < tree.n; i++) {
            tree.key[i] = Integer.parseInt(tok.nextToken());
            tree.left[i] = Integer.parseInt(tok.nextToken());
            tree.right[i] = Integer.parseInt(tok.nextToken());
        }

        Integer[] resultInOrder = new Integer[tree.n];
        Integer[] resultPreOrder = new Integer[tree.n];
        Integer[] resultPostOrder = new Integer[tree.n];
        tok = new StringTokenizer(result);

        for (int i = 0; i < tree.n; i++) {
            resultInOrder[i] = Integer.parseInt(tok.nextToken());
        }
        for (int i = 0; i < tree.n; i++) {
            resultPreOrder[i] = Integer.parseInt(tok.nextToken());
        }
        for (int i = 0; i < tree.n; i++) {
            resultPostOrder[i] = Integer.parseInt(tok.nextToken());
        }

        Integer[] resultArray = new Integer[tree.n];
        resultArray = tree.inOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resultInOrder)) {
            System.out.println("Wrong inOrder");
        }

        resultArray = new Integer[tree.n];
        resultArray = tree.preOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resultPreOrder)) {
            System.out.println("Wrong preOrder");
        }

        resultArray = new Integer[tree.n];
        resultArray = tree.postOrder().toArray(resultArray);

        if (!Arrays.equals(resultArray, resultPostOrder)) {
            System.out.println("Wrong postOrder");
            for (int i = 0; i < tree.n; i++) {
                if (resultArray[i] != resultPostOrder[i]) {
                    System.out.println(resultArray[i] + " " + i + " " + resultPostOrder[i]);
                    break;
                }
            }
        }
    }
}
