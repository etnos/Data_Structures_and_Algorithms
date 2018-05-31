package assignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();

        // write your code here
        Map<Character, Integer> root = new HashMap<>();
        trie.add(root);
        Map<Character, Integer> currentNode;
        char currentSymbol;
        for (String pattern : patterns) {
            currentNode = root;
            for (int i = 0; i < pattern.length(); i++) {
                currentSymbol = pattern.charAt(i);
                if (currentNode.containsKey(currentSymbol)) {
                    int vertexId = currentNode.get(currentSymbol);
                    currentNode = trie.get(vertexId);
                } else {
                    int newVertexId = trie.size();
                    Map<Character, Integer> newNode = new HashMap<>();
                    trie.add(newNode);
                    currentNode.put(currentSymbol, newVertexId);
                    currentNode = newNode;
                }
            }
        }

        return trie;
    }

    static public void main(String[] args) throws IOException {
        courseraMain();
//        new assignment1.Trie().test();
    }

    private static void courseraMain() throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }


    private void test() {
        String[] patterns = new String[]{"ATA"};
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);

        System.out.println();
        patterns = new String[]{"AT", "AG", "AC"};
        trie = buildTrie(patterns);
        print(trie);

        System.out.println();
        patterns = new String[]{"ATAGA", "ATC", "GAT"};
        trie = buildTrie(patterns);
        print(trie);
    }
}
