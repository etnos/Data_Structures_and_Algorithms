package assignment4;

import java.util.*;
import java.io.*;

public class is_bst {
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

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = newNode(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        Node newNode(int k, int cL, int cR) {
            return new Node(k, cL, cR);
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here

            // to check is it a Binary search tree
            // check is inOrder traversal make a sorted array
            if (tree.length > 1) {
                Stack<Integer> stack = new Stack<>();
                stack.push(0);
                int prev = Integer.MIN_VALUE;
                int i;
                while (!stack.isEmpty()) {
                    i = stack.peek();
                    if (tree[i].left != -1) {
                        stack.push(tree[i].left);
                        tree[i].left = -1;
                    } else {
                        stack.pop();
                        if (tree[i].key > prev) {
                            prev = tree[i].key;
                        } else {
                            return false;
                        }
                        if (tree[i].right != -1) {
                            stack.push(tree[i].right);
                            tree[i].right = -1;
                        }
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
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
//        new assignment4.is_bst().test();
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
        tree.nodes = 0;
        tree.tree = new IsBST.Node[tree.nodes];

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

        tree = new IsBST();
        tree.nodes = 4;
        tree.tree = new IsBST.Node[tree.nodes];
        tree.tree[0] = tree.newNode(4, 1, -1);
        tree.tree[1] = tree.newNode(2, 2, 3);
        tree.tree[2] = tree.newNode(1, -1, -1);
        tree.tree[3] = tree.newNode(5, -1, -1);

        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

    }
}
