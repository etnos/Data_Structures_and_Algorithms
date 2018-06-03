package assignment1;

import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {

    class Node {
        String label;
        Map<Character, Node> child = new HashMap<>();

        public Node(String label) {
            this.label = label;
        }
    }

    private int lastIndex = 0;

    private class TrieNode {
        public char edgeLetter;
        public int nodeIndex;
        public int parentIndex;


        private List<TrieNode> edges = new ArrayList<>();

        public TrieNode(char edgeLetter, int parentIndex, ArrayList<String> result) {  // add argument result list
            this.edgeLetter = edgeLetter;
            nodeIndex = lastIndex++;
            if (nodeIndex > 0) {
                result.add(parentIndex + "->" + nodeIndex + ":" + edgeLetter);
            }
        }

        public TrieNode addEdge(char edgeLetter, ArrayList<String> result) {
            edges.add(new TrieNode(edgeLetter, this.nodeIndex, result));
            return this;
        }

        public void checkEdges(String pattern, ArrayList<String> result) {
            boolean newEdge = true;
            if (pattern.length() > 0) {
                for (TrieNode edge : edges) {
                    if (edge.edgeLetter == pattern.charAt(0)) {
                        edge.checkEdges(pattern.substring(1), result);
                        newEdge = false;
                        break;
                    }
                }

                if (newEdge) {
                    TrieNode newNode = addEdge(pattern.charAt(0), result);
                    this.edges.get(edges.size() - 1).checkEdges(pattern.substring(1), result);
                }
            }
        }
    }

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

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public Node computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<String>();
        // Implement this function yourself

        Node root = new Node("");
        root.child.put(text.charAt(0), new Node(text));
        Node current, child;
        String label;
        int j, k;
        for (int i = 1; i < text.length(); i++) {
            current = root;
            j = i;
            while (j < text.length()) {
                if (current.child.containsKey(text.charAt(j))) {
                    child = current.child.get(text.charAt(j));
                    label = child.label;
                    k = j + 1;

                    // TODO this one does not work, it is not correct
                    while (k - j < label.length() && k < text.length()) {
                        k++;
                        if (k - j == label.length()) {
                            current = child;
                            j = k;
                        } else {
                            char cExist = label.charAt(k - j);
                            char cNew = text.charAt(k);
                            Node mid = new Node(label.substring(0, k - j));
                            mid.child.put(cNew, new Node(text.substring(k)));
                            mid.child.put(cExist, child);
                            child.label = label.substring(k - j);
                            current.child.put(text.charAt(j), mid);
                        }
                    }
                } else {
                    current.child.put(text.charAt(j), new Node(text.substring(j)));

                }
                j++;
            }
        }

        return root;
    }

    public List<String> computeSuffixTreeEdges(ArrayList<String> text) {
        ArrayList<String> result = new ArrayList<>();
        TrieNode rootNode = new TrieNode((char) 0, 0, result);
        for (int i = 0; i < text.size(); i++) {
            String pattern = text.get(i);
            //It's not working because it's starting at the top and
            //there is nothing there. Have to go to the next one
            //somehow.
            rootNode.checkEdges(pattern, result);
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
//        new SuffixTree().run();
        new SuffixTree().test();
    }

//    public void print(List<String> x) {
//        for (String a : x) {
//            System.out.println(a);
//        }
//    }

    public void print(Node root) {
        Set<Character> characterSet = root.child.keySet();
        for (Character c : characterSet) {
            System.out.println(root.child.get(c).label);
            print(root.child.get(c));
        }

    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
//        List<String> edges = computeSuffixTreeEdges(text);
//        print(edges);
    }

    private void test() {
        String text = "A$";
        Node root = computeSuffixTreeEdges(text);
        print(root);

        System.out.println();

        text = "ACA$";
        root = computeSuffixTreeEdges(text);
        print(root);

//        System.out.println();
//
//        text = "ATAAATG$";
//        root = computeSuffixTreeEdges(text);
//        print(root);
    }
}
