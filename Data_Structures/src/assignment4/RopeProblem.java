package assignment4;

import java.io.*;
import java.util.*;

public class RopeProblem {
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

    class Rope {
        Vertex root;

        // Splay tree implementation
        // copied from previous task
        // Vertex of a splay tree
        class Vertex {
            char key;
            // Sum of all the keys in the subtree - remember to update
            // it after each operation that changes the tree.
            int sum;
            Vertex left;
            Vertex right;
            Vertex parent;

            Vertex(char key, int sum, Vertex left, Vertex right, Vertex parent) {
                this.key = key;
                this.sum = sum;
                this.left = left;
                this.right = right;
                this.parent = parent;
            }
        }

        void update(Vertex v) {
            if (v == null) return;
            v.sum = 1 + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
            if (v.left != null) {
                v.left.parent = v;
            }
            if (v.right != null) {
                v.right.parent = v;
            }
        }

        void smallRotation(Vertex v) {
            Vertex parent = v.parent;
            if (parent == null) {
                return;
            }
            Vertex grandparent = v.parent.parent;
            if (parent.left == v) {
                Vertex m = v.right;
                v.right = parent;
                parent.left = m;
            } else {
                Vertex m = v.left;
                v.left = parent;
                parent.right = m;
            }
            update(parent);
            update(v);
            v.parent = grandparent;
            if (grandparent != null) {
                if (grandparent.left == parent) {
                    grandparent.left = v;
                } else {
                    grandparent.right = v;
                }
            }
        }

        void bigRotation(Vertex v) {
            if (v.parent.left == v && v.parent.parent.left == v.parent) {
                // Zig-zig
                smallRotation(v.parent);
                smallRotation(v);
            } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
                // Zig-zig
                smallRotation(v.parent);
                smallRotation(v);
            } else {
                // Zig-zag
                smallRotation(v);
                smallRotation(v);
            }
        }

        // Makes splay of the given vertex and returns the new root.
        Vertex splay(Vertex v) {
            if (v == null) return null;
            while (v.parent != null) {
                if (v.parent.parent == null) {
                    smallRotation(v);
                    break;
                }
                bigRotation(v);
            }
            return v;
        }

        class VertexPair {
            Vertex left;
            Vertex right;

            VertexPair() {
            }

            VertexPair(Vertex left, Vertex right) {
                this.left = left;
                this.right = right;
            }
        }

        // Searches for the given key in the tree with the given root
        // and calls splay for the deepest visited node after that.
        // Returns pair of the result and the new root.
        // If found, result is a pointer to the node with the given key.
        // Otherwise, result is a pointer to the node with the smallest
        // bigger key (next value in the order).
        // If the key is bigger than all keys in the tree,
        // then result is null.
        VertexPair find(Vertex root, int key) {
            if (root.sum < key) return null;
            Vertex v = root;
            Vertex last = root;
            Vertex next = null;
            int leftSum = 0;

            while (v != null) {
                last = v;
                leftSum = 0;
                if (v.left != null) {
                    leftSum = v.left.sum;
                }
                if (key == leftSum + 1) {
                    next = v;
                    break;
                } else if (key > leftSum) {
                    v = v.right;
                    key -= leftSum + 1;
                } else {
                    v = v.left;
                }
            }
            root = splay(last);
            return new VertexPair(next, root);
        }

        VertexPair split(Vertex root, int key) {
            if (root == null) return new VertexPair();
            VertexPair result = new VertexPair();
            VertexPair findAndRoot = find(root, key);
            if (findAndRoot != null) {
                root = findAndRoot.right;
                result.right = findAndRoot.left;
            }
            if (result.right == null) {
                result.left = root;
                return result;
            }
            result.right = splay(result.right);
            result.left = result.right.left;
            result.right.left = null;
            if (result.left != null) {
                result.left.parent = null;
            }
            update(result.left);
            update(result.right);
            return result;
        }

        Vertex merge(Vertex left, Vertex right) {
            if (left == null) return right;
            if (right == null) return left;
            while (right.left != null) {
                right = right.left;
            }
            right = splay(right);
            right.left = left;
            update(right);
            return right;
        }

        Rope(String s) {
            if (s.length() != 0) {
                if (s.length() == 1) {
                    // only 1 element
                    root = new Vertex(s.charAt(0), 1, null, null, null);
                } else {
                    // more than 1 elements
                    root = new Vertex(s.charAt(0), s.length(), null, null, null);

                    Vertex node;
                    Vertex parent = root;
                    for (int i = 1; i < s.length(); i++) {
                        node = new Vertex(s.charAt(i), s.length() - i, null, null, parent);
                        // assign right child
                        parent.right = node;
                        // move next
                        parent = parent.right;
                    }
                }
            }
        }

        String processNaive(String s, int i, int j, int k) {
            // Replace this code with a faster implementation
            String t = s.substring(0, i) + s.substring(j + 1);
            return t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
        }


        void process(int i, int j, int k) {
            VertexPair leftMid = split(root, i);
            VertexPair midRight = split(leftMid.right, j - i + 2);
            Vertex mid = midRight.left;
            root = merge(leftMid.left, midRight.right);
            VertexPair leftRight = split(root, k + 1);
            root = merge(leftRight.left, mid);
            root = merge(root, leftRight.right);
        }

        private String result() {
            StringBuilder buf = new StringBuilder();
            inOrderTraverse(root, buf, new Stack<>());
            return buf.toString();
        }

        private StringBuilder inOrderTraverse(Vertex node, StringBuilder buf, Stack<Vertex> stack) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            // traverse the tree
            while (stack.size() > 0) {

                // visit the top node
                node = stack.pop();
                buf.append(node.key);
                if (node.right != null) {
                    node = node.right;

                    // the next node to be visited is the leftmost
                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
            }
            return buf;
        }
    }

    public static void main(String[] args) throws IOException {
        new RopeProblem().run();
//        new assignment4.RopeProblem().test();
//        new assignment4.RopeProblem().test2();
//        new assignment4.RopeProblem().autoTest();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt() + 1;
            int j = in.nextInt() + 1;
            int k = in.nextInt();
            rope.process(i, j, k);
        }
        out.println(rope.result());
        out.close();
    }

    private void test() {
        String s = "hlelowrold";
        int q = 2;
        int[] i = new int[]{1, 6};
        int[] j = new int[]{1, 6};
        int[] k = new int[]{2, 7};

        Rope rope = new Rope(s);
        for (int r = 0; r < q; r++) {
            rope.process(i[r] + 1, j[r] + 1, k[r]);
        }

        System.out.println("old s = " + s + " new s = " + rope.result());

        s = "abcdef";
        q = 2;
        i = new int[]{0, 4};
        j = new int[]{1, 5};
        k = new int[]{1, 0};

        rope = new Rope(s);
        for (int r = 0; r < q; r++) {
            rope.process(i[r] + 1, j[r] + 1, k[r]);
        }

        System.out.println("old s = " + s + " new s = " + rope.result());
    }

    private void test2() {
        String s = "12345";

        RopeProblem.Rope rope = new RopeProblem.Rope(s);
        rope.process(1, 2, 3);

        System.out.println(rope.result());
        System.out.println("End");
    }

    private void autoTest() {
        Random generator = new Random();
        int actionsAmount = 1000;
        int counter = 0;
        while (true) {
            int strLength = 100000;
            String str = randomString(strLength);
            String str2 = str;

            strLength = str.length();

            if (strLength <= 0) {
                System.out.println();
            }

            int q = generator.nextInt(actionsAmount);
            int[] i = new int[q];
            int[] j = new int[q];
            int[] k = new int[q];

            for (int t = 0; t < q; t++) {
                i[t] = generator.nextInt(strLength);
                j[t] = generator.nextInt(strLength);
                j[t] = Math.max(i[t], j[t]);
                int kBound = strLength - (j[t] - i[t]);
                k[t] = generator.nextInt(kBound);
                if (i[t] < 0 || j[t] < 0 || k[t] < 0) {
                    System.out.println("check");
                }
            }

            Rope rope = new Rope(str);
            for (int r = 0; r < i.length; r++) {
                rope.process(i[r] + 1, j[r] + 1, k[r]);
            }


            for (int r = 0; r < i.length; r++) {
                str2 = rope.processNaive(str2, i[r], j[r], k[r]);
            }

            String result = rope.result();
            if (!result.equals(str2)) {
                System.out.println("Wrong");
                break;
            } else {
                System.out.println(++counter);
            }
        }
    }

    private String randomString(int length) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char tempChar;
        for (int i = 0; i < length; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
