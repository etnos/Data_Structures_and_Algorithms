package assignment3;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrencesFast(readInput()));
        out.close();

//        test();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrencesNaive(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i + m <= n; ++i) {
            boolean equal = true;
            for (int j = 0; j < m; ++j) {
                if (s.charAt(j) != t.charAt(i + j)) {
                    equal = false;
                    break;
                }
            }
            if (equal)
                occurrences.add(i);
        }
        return occurrences;
    }

    private static List<Integer> getOccurrencesFast(Data input) {
        int p = input.pattern.length(), n = input.text.length();
        List<Integer> occurrences = new ArrayList<>();

        long[] hashes = PrecomputeHashes(input);

        long patternHash = hashFunc(input.pattern);
        char[] array = input.text.toCharArray();

        for (int i = 0; i <= n - p; i++) {
            if (patternHash == hashes[i]) {
                //In Java, however, method substring does NOT create a new String. Avoid using new String where
                //it is not needed, just use substring.
                // @ ME -> not sure about this statement, in Java substring() you can see new String().
                // maybe it depends on JVM. Be safe, use char[]
                if (AreEqual(array, i, input.pattern)) {
                    occurrences.add(i);
                }
            }
        }

        return occurrences;
    }


    private static boolean AreEqual(char[] strAsCharArray, int i, String str2) {
        for (int j = 0; j < str2.length(); j++, i++) {
            if (strAsCharArray[i] != str2.charAt(j)) {
                return false;
            }
        }

        return true;
    }

    private static long[] PrecomputeHashes(Data input) {
        String text = input.text;
        int textLength = text.length();
        int patternLength = input.pattern.length();
        long[] hashes = new long[textLength - patternLength + 1];
        hashes[textLength - patternLength] = hashFunc(text.substring(textLength - patternLength));

        long y = 1;
        for (int i = 1; i <= patternLength; i++) {
            y = (y * multiplier) % prime;
        }

        for (int i = textLength - patternLength - 1; i >= 0; i--) {
            long l = hashes[i + 1] * multiplier + text.charAt(i) - y * text.charAt(i + patternLength);
            // Beware of taking negative numbers (mod 𝑝). In many programming languages, (−2)%5 ̸= 3%5. Thus
            //you can compute the same hash values for two strings, but when you compare them, they appear to
            //be different. To avoid this issue, you can use such construct in the code: 𝑥 ← ((𝑎%𝑝) + 𝑝)%𝑝 instead
            //of just 𝑥 ← 𝑎%𝑝.
            hashes[i] = ((l % prime) + prime) % prime;
        }
        return hashes;
    }

    private static long prime = longRandomPrime();
    private static int multiplier = 31;

    private static long hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return hash;
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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

    private static void test() {
        String text = "abacaba";
        String pattern = "aba";

        List<Integer> occurrences = getOccurrencesFast(new Data(pattern, text));

        for (Integer position : occurrences) {
            System.out.print(position);
            System.out.print(" ");
        }

        System.out.println();
        text = "testTesttesT";
        pattern = "Test";

        occurrences = getOccurrencesFast(new Data(pattern, text));

        for (Integer position : occurrences) {
            System.out.print(position);
            System.out.print(" ");
        }


        System.out.println();
        text = "baaaaaaa";
        pattern = "aaaaa";

        occurrences = getOccurrencesFast(new Data(pattern, text));

        for (Integer position : occurrences) {
            System.out.print(position);
            System.out.print(" ");
        }

        System.out.println();
        text = "ZtonpqnFzlpvUKZrBbRlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXxtHmTxoLuMbRYsvSpxhtrlvABBlFYmndFzHypOmJyFxjHEPlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXbDiEAvtPlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXRRNoBCUMQVOlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXRLKlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXAYPDKWtVpShhclNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXOJlUlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXglmlNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUXuaOibGlVrwghvNTgLfltIbEdBlgjelFjQkBeFrdEV";
        pattern = "lNoYhXmlwOscxnkTWjsyNJNhgvzMFbxFnbiWuBAGjZQlCRQHjTUX";
        Integer[] result = new Integer[]{19, 118, 178, 241, 296, 361, 417, 472};

        occurrences = getOccurrencesFast(new Data(pattern, text));

        if (!Arrays.equals(result, occurrences.toArray(new Integer[0]))) {
            System.out.println("Wrong");
        }
    }

}

