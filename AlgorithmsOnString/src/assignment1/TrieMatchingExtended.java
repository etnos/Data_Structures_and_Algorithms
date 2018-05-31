package assignment1;

import java.io.*;
import java.util.*;

class NodeExtended {
    public static final int Letters = 4;
    public static final int NA = -1;
    public int next[];
    public boolean patternEnd;

    NodeExtended() {
        next = new int[Letters];
        Arrays.fill(next, NA);
        patternEnd = false;
    }
}

public class TrieMatchingExtended implements Runnable {
    int letterToIndex(char letter) {
        switch (letter) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
            default:
                assert (false);
                return Node.NA;
        }
    }

    List<Integer> solve(String text, int n, List<String> patterns) {
        List<Integer> result = new ArrayList<Integer>();
        Set<Integer> resultSet = new HashSet<>();

        // write your code here
        List<Map<Character, Integer>> trie = buildTrie(patterns);

        for (int i = 0; i < text.length(); i++) {
            if (prefixTrieMatching(i, text, trie, result, resultSet)) {
                if (!resultSet.contains(i)) {
                    result.add(i);
                    resultSet.add(i);
                }
            }
        }

        return result;
    }

    private boolean prefixTrieMatching(int index, String text, List<Map<Character, Integer>> trie, List<Integer> result, Set<Integer> resultSet) {
        char symbol = text.charAt(index);
        int startIndex = index;
        Map<Character, Integer> v = trie.get(0);
        if (v.size() == 0) {
            return false;
        }
        while (true) {
            if (v.containsKey(symbol)) {
                v = trie.get(v.get(symbol));
                if (v.size() == 1 && v.containsKey('$')) {
                    return true;
                } else if (v.containsKey('$') && !resultSet.contains(startIndex)) {
                    result.add(startIndex);
                    resultSet.add(startIndex);
                }
                index++;
                if (index >= text.length()) {
                    return false;
                }
                symbol = text.charAt(index);
            } else {
                return false;
            }

        }
    }

    List<Map<Character, Integer>> buildTrie(List<String> patterns) {
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

            // add END node
            int newVertexId = trie.size();
            Map<Character, Integer> newNode = new HashMap<>();
            trie.add(newNode);
            currentNode.put('$', newVertexId);
        }

        return trie;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String text = in.readLine();
            int n = Integer.parseInt(in.readLine());
            List<String> patterns = new ArrayList<String>();
            for (int i = 0; i < n; i++) {
                patterns.add(in.readLine());
            }

            List<Integer> ans = solve(text, n, patterns);

            for (int j = 0; j < ans.size(); j++) {
                System.out.print("" + ans.get(j));
                System.out.print(j + 1 < ans.size() ? " " : "\n");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        courserMain();
//        new assignment1.TrieMatchingExtended().test();
    }

    private static void courserMain() {
        new Thread(new TrieMatchingExtended()).start();
    }

    private void test() {
        String text = "AAA";
        int n = 1;
        List<String> patterns = new ArrayList<String>();
        patterns.add("AA");

        List<Integer> ans = solve(text, n, patterns);

        for (int j = 0; j < ans.size(); j++) {
            System.out.print("" + ans.get(j));
            System.out.print(j + 1 < ans.size() ? " " : "\n");
        }

        System.out.println();
        text = "ACATA";
        n = 3;
        patterns = new ArrayList<String>();
        patterns.add("AT");
        patterns.add("A");
        patterns.add("AG");

        ans = solve(text, n, patterns);

        for (int j = 0; j < ans.size(); j++) {
            System.out.print("" + ans.get(j));
            System.out.print(j + 1 < ans.size() ? " " : "\n");
        }
    }
}
