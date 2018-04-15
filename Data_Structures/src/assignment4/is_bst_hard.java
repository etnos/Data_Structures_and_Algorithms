package assignment4;

import java.util.*;
import java.io.*;

public class is_bst_hard {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        class HelperNode {
            Node n;
            long min;
            long max;

            public HelperNode(Node n, long min, long max) {
                this.n = n;
                this.min = min;
                this.max = max;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        Node newNode(int k, int cL, int cR) {
            return new Node(k, cL, cR);
        }


        boolean isBinarySearchTree() {
            // Implement correct algorithm here

            if (tree.length > 1) {
                Stack<HelperNode> stackHelper = new Stack<>();
                stackHelper.push(new HelperNode(tree[0], Long.MIN_VALUE, Long.MAX_VALUE));
                HelperNode helperNode;
                while (!stackHelper.isEmpty()) {
                    helperNode = stackHelper.pop();

                    if (helperNode.n.key < helperNode.min || helperNode.n.key >= helperNode.max) {
                        return false;
                    }

                    if (helperNode.n.left != -1) {
                        stackHelper.push(new HelperNode(tree[helperNode.n.left], helperNode.min, helperNode.n.key));
                    }
                    if (helperNode.n.right != -1) {
                        stackHelper.push(new HelperNode(tree[helperNode.n.right], helperNode.n.key, helperNode.max));
                    }
                }
            }
            return true;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();

//        new assignment4.is_bst_hard().test();
    }

    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }

    private void test() {
        IsBST tree = new IsBST();
        tree.nodes = 3;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(2, 1, 2);
        tree.tree[1] = tree.newNode(1, -1, -1);
        tree.tree[2] = tree.newNode(3, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }


        tree = new IsBST();
        tree.nodes = 3;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(1, 1, 2);
        tree.tree[1] = tree.newNode(2, -1, -1);
        tree.tree[2] = tree.newNode(3, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

        tree = new IsBST();
        tree.nodes = 3;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(2, 1, 2);
        tree.tree[1] = tree.newNode(1, -1, -1);
        tree.tree[2] = tree.newNode(2, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

        tree = new IsBST();
        tree.nodes = 3;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(2, 1, 2);
        tree.tree[1] = tree.newNode(2, -1, -1);
        tree.tree[2] = tree.newNode(3, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

        tree = new IsBST();
        tree.nodes = 0;
        tree.tree = new IsBST.Node[tree.nodes];

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

        tree = new IsBST();
        tree.nodes = 1;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(2147483647, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }


        tree = new IsBST();
        tree.nodes = 5;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(1, -1, 1);
        tree.tree[1] = tree.newNode(2, -1, 2);
        tree.tree[2] = tree.newNode(3, -1, 3);
        tree.tree[3] = tree.newNode(4, -1, 4);
        tree.tree[4] = tree.newNode(5, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

        tree = new IsBST();
        tree.nodes = 7;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(4, 1, 2);
        tree.tree[1] = tree.newNode(2, 3, 4);
        tree.tree[2] = tree.newNode(6, 5, 6);
        tree.tree[3] = tree.newNode(1, -1, -1);
        tree.tree[4] = tree.newNode(3, -1, -1);
        tree.tree[5] = tree.newNode(5, -1, -1);
        tree.tree[6] = tree.newNode(7, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
