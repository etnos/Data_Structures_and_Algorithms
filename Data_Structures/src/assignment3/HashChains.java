package assignment3;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    private ArrayList<LinkedList<String>> hashMap;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
//        test();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int) hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
//        System.out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
//         out.flush();
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                if (!elems.contains(query.s))
                    elems.add(0, query.s);
                break;
            case "del":
                if (elems.contains(query.s))
                    elems.remove(query.s);
                break;
            case "find":
                writeSearchResult(elems.contains(query.s));
                break;
            case "check":
                for (String cur : elems) {
                    if (hashFunc(cur) == query.ind) {
                        System.out.print(cur + " ");
//                        out.print(cur + " ");
                    }
                }
//                out.println();
                System.out.println();
                // Uncomment the following if you want to play with the program interactively.
//                 out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        initMap(bucketCount);
        for (int i = 0; i < queryCount; ++i) {
            processQueryFast(readQuery());
        }
        out.close();
    }


    //        int hashMapSize = 1000;
//
    private void initMap(int size) {
        hashMap = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            hashMap.add(new LinkedList<>());
        }
    }


    private void processQueryFast(Query query) {
        switch (query.type) {
            case "add":
                add(query.s);
                break;
            case "del":
                del(query.s);
                break;
            case "find":
                writeSearchResult(find(query.s));
                break;
            case "check":
                check(query.ind);
                out.println();
//                System.out.println();
                // Uncomment the following if you want to play with the program interactively.
//                 out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void add(String str) {
        int index = hashFunc(str);
        LinkedList<String> strings = hashMap.get(index);


        for (String storedString : strings) {
            if (storedString.equals(str)) {
                return;
            }
        }
        strings.addFirst(str);
    }

    public boolean find(String str) {
        int index = hashFunc(str);
        LinkedList<String> strings = hashMap.get(index);
        for (String string : strings) {
            if (string.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void check(int hash) {
        LinkedList<String> strings = hashMap.get(hash);
        for (String string : strings) {
//            System.out.print(string + " ");
            out.print(string + " ");
        }
    }

    public void del(String str) {
        int index = hashFunc(str);
        LinkedList<String> strings = hashMap.get(index);
        strings.remove(str);
    }


    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static void test() {
        HashChains hashChains = new HashChains();
        hashChains.bucketCount = 5;
        hashChains.initMap(hashChains.bucketCount);
        hashChains.elems = new ArrayList<>();
        hashChains.out = new PrintWriter(new BufferedOutputStream(System.out));

        List<Query> list = new ArrayList<>();
        list.add(new Query("add", "world"));
        list.add(new Query("add", "HellO"));
        list.add(new Query("check", 4));
        list.add(new Query("find", "World"));
        list.add(new Query("find", "world"));
        list.add(new Query("del", "world"));
        list.add(new Query("check", 4));
        list.add(new Query("del", "HellO"));
        list.add(new Query("add", "luck"));
        list.add(new Query("add", "GooD"));
        list.add(new Query("check", 2));
        list.add(new Query("del", "good"));

        for (int i = 0; i < list.size(); i++) {
            hashChains.processQueryFast(list.get(i));

        }


        System.out.println();

        hashChains = new HashChains();
        hashChains.bucketCount = 4;
        hashChains.initMap(hashChains.bucketCount);
        hashChains.elems = new ArrayList<>();
        hashChains.out = new PrintWriter(new BufferedOutputStream(System.out));

        list = new ArrayList<>();
        list.add(new Query("add", "test"));
        list.add(new Query("add", "test"));
        list.add(new Query("find", "test"));
        list.add(new Query("del", "test"));
        list.add(new Query("find", "test"));
        list.add(new Query("find", "Test"));
        list.add(new Query("add", "Test"));
        list.add(new Query("find", "Test"));

        for (int i = 0; i < list.size(); i++) {
            hashChains.processQueryFast(list.get(i));

        }
    }
}
