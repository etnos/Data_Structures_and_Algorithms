package assignment1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class tree_height {
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

    public class TreeHeight {
        int n;
        int parent[];

        Node root;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeight() {

            // init tree with data
            buildTree();

            LinkedList<Node> queue = new LinkedList<>();
            LinkedList<Node> queue2 = new LinkedList<>();
            LinkedList<Node> temp;
            queue.add(root);

            int height = 0;
            Node current;
            while (!queue.isEmpty()) {
                current = queue.poll();
                queue2.addAll(current.children);

                if (queue.isEmpty()) {
                    height++;
                    temp = queue;
                    queue = queue2;
                    queue2 = temp;
                }
            }

            return height;
        }

        private void buildTree() {
            tree = new Node[n];
            for (int i = 0; i < n; i++) {
                tree[i] = new Node();
            }

            for (int i = 0; i < n; i++) {
                if (parent[i] == -1) {
                    root = tree[i];
                } else {
                    tree[parent[i]].children.add(tree[i]);
                }
            }
        }
    }

    class Node {
        LinkedList<Node> children = new LinkedList<>();
    }

    static public void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();

//        new tree_height().test();
//        new .tree_height().generatedTest();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }

    private void test() {
        testCase(new int[]{4, -1, 4, 1, 1}, 3);
        testCase(new int[]{-1, 0, 4, 0, 3}, 4);
    }

    private void generatedTest() {
        try {
            String name;
            for (int i = 1; i <= 24; i++) {
                if (i < 10) {
                    name = "0" + String.valueOf(i);
                } else {
                    name = String.valueOf(i);
                }
                String data = new String(Files.readAllBytes(Paths.get("src/assignment1/tree/tests/" + name)));
                String result = new String(Files.readAllBytes(Paths.get("src/assignment1/tree/tests/" + name + ".a")));

                StringTokenizer tok = new StringTokenizer(data);
                int n = Integer.parseInt(tok.nextToken());

                int[] array = new int[n];
                for (int j = 0; j < n; j++) {
                    array[j] = Integer.parseInt(tok.nextToken());
                }

                testCase(array, Integer.parseInt(result.trim()), name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void testCase(int[] tree, int resultExpected) {
        testCase(tree, resultExpected, "");
    }

    private void testCase(int[] tree, int resultExpected, String fileName) {
        TreeHeight treeHeight = new TreeHeight();
        treeHeight.n = tree.length;
        treeHeight.parent = tree;
        int resultLocal = treeHeight.computeHeight();
        if (resultLocal != resultExpected) {
            System.out.println("WRONG " + resultLocal + " file: " + fileName + " text: " + Arrays.toString(tree));
            System.exit(0);
        }
    }
}
