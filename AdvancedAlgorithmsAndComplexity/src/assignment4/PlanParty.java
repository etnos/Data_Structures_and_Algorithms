package assignment4;

import java.io.*;
import java.util.*;

class Vertex {
    Vertex() {
        this.weight = 0;
        this.children = new ArrayList<Integer>();
    }

    int weight;
    ArrayList<Integer> children;
}

class PlanParty {
    static Vertex[] ReadTree() throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);

        tokenizer.nextToken();
        int vertices_count = (int) tokenizer.nval;

        Vertex[] tree = new Vertex[vertices_count];

        for (int i = 0; i < vertices_count; ++i) {
            tree[i] = new Vertex();
            tokenizer.nextToken();
            tree[i].weight = (int) tokenizer.nval;
        }

        for (int i = 1; i < vertices_count; ++i) {
            tokenizer.nextToken();
            int from = (int) tokenizer.nval;
            tokenizer.nextToken();
            int to = (int) tokenizer.nval;
            tree[from - 1].children.add(to - 1);
            tree[to - 1].children.add(from - 1);
        }

        return tree;
    }

    private static Vertex[] readTreeTest1() {

        int vertices_count = 1;

        Vertex[] tree = new Vertex[vertices_count];

        for (int i = 0; i < vertices_count; ++i) {
            tree[i] = new Vertex();
            tree[i].weight = 1000;
        }

        return tree;
    }

    private static Vertex[] readTreeTest2() {

        int vertices_count = 2;

        Vertex[] tree = new Vertex[vertices_count];

        int[] funFactor = new int[]{1, 2};
        for (int i = 0; i < vertices_count; ++i) {
            tree[i] = new Vertex();
            tree[i].weight = funFactor[i];
        }

        int[][] subordinationStructure = new int[][]{{1, 2}};
        for (int i = 1; i < vertices_count; ++i) {
            int from = subordinationStructure[i - 1][0];
            int to = subordinationStructure[i - 1][1];
            tree[from - 1].children.add(to - 1);
            tree[to - 1].children.add(from - 1);
        }

        return tree;
    }

    private static Vertex[] readTreeTest3() {

        int vertices_count = 5;

        Vertex[] tree = new Vertex[vertices_count];

        int[] funFactor = new int[]{1, 5, 3, 7, 5};
        for (int i = 0; i < vertices_count; ++i) {
            tree[i] = new Vertex();
            tree[i].weight = funFactor[i];
        }

        int[][] subordinationStructure = new int[][]{{5, 4}, {2, 3}, {4, 2}, {1, 2}};
        for (int i = 1; i < vertices_count; ++i) {
            int from = subordinationStructure[i - 1][0];
            int to = subordinationStructure[i - 1][1];
            tree[from - 1].children.add(to - 1);
            tree[to - 1].children.add(from - 1);
        }

        return tree;
    }

    static void dfs(Vertex[] tree, int vertex, int parent, int[] mIn, int[] mOut) {
        List<Integer> children = tree[vertex].children;
        for (int child : children) {
            if (child != parent)
                dfs(tree, child, vertex, mIn, mOut);
        }
        int weight = tree[vertex].weight;
        boolean isALeaf = children.isEmpty();
        if (isALeaf) {
            mIn[vertex] = weight;
            mOut[vertex] = 0;
        } else {
            mIn[vertex] = weight;
            for (int child : children) {
                mIn[vertex] += mOut[child];
                mOut[vertex] += Math.max(mIn[child], mOut[child]);
            }
        }
    }

    static int MaxWeightIndependentTreeSubset(Vertex[] tree) {
        int size = tree.length;
        if (size == 0) {
            return 0;
        }
        int root = 0;
        int[] mIn = new int[size];
        int[] mOut = new int[size];
        dfs(tree, root, -1, mIn, mOut);
        return Math.max(mIn[root], mOut[root]);
    }

    public static void main(String[] args) throws IOException {
        // This is to avoid stack overflow issues
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new PlanParty().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        Vertex[] tree = ReadTree();
        int weight = MaxWeightIndependentTreeSubset(tree);
        System.out.println(weight);


//        assignment4.Vertex[] tree = readTreeTest1();
//        int weight = MaxWeightIndependentTreeSubset(tree);
//        System.out.println(weight);
//
//        System.out.println();
//        tree = readTreeTest2();
//        weight = MaxWeightIndependentTreeSubset(tree);
//        System.out.println(weight);
//
//        System.out.println();
//        tree = readTreeTest3();
//        weight = MaxWeightIndependentTreeSubset(tree);
//        System.out.println(weight);
    }
}
