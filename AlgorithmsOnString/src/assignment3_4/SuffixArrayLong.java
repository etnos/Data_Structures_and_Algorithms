package assignment3_4;

import java.util.*;
import java.io.*;

public class SuffixArrayLong {
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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text) {
        // write your code here
        int tl = text.length();
        int[] order = sortCharacters(text);
        int[] cls = computeCharClasses(text, order);

        int l = 1;
        while (l < tl) {
            order = sortDoubled(text, l, order, cls);
            cls = updateClasses(order, cls, l);
            l *= 2;
        }

        return order;
    }

    private int[] sortCharacters(String s) {
        int textLength = s.length();
        int order[] = new int[textLength];
        int count[] = new int[5];
        for (int i = 0; i < textLength; i++)
            count[EquivalenceClass.ltn(s.charAt(i))]++;
        for (int i = 1; i < 5; i++)
            count[i] = count[i] + count[i - 1];
        int c;
        for (int i = textLength - 1; i >= 0; i--) {
            c = EquivalenceClass.ltn(s.charAt(i));
            count[c]--;
            order[count[c]] = i;
        }
        return order;
    }

    private int[] sortDoubled(String s, int l, int[] order, int[] cls) {
        int sl = s.length();
        int[] count = new int[sl];
        int[] newOrder = new int[sl];
        for (int i = 0; i < sl; i++) {
            count[cls[i]]++;
        }
        for (int i = 1; i < sl; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (int i = sl - 1; i >= 0; i--) {
            int start = (order[i] - l + sl) % sl;
            int cl = cls[start];
            count[cl] = count[cl] - 1;
            newOrder[count[cl]] = start;
        }
        return newOrder;
    }

    public int[] updateClasses(int[] newOrder, int[] cls, int l) {
        int n = newOrder.length;
        int[] newClass = new int[n];
        newClass[newOrder[0]] = 0;
        int cur, mid, prev, midPrev;
        for (int i = 1; i < n; i++) {
            cur = newOrder[i];
            prev = newOrder[i - 1];
            mid = (cur + l) % n;
            midPrev = (prev + l) % n;
            if (cls[cur] != cls[prev] || cls[mid] != cls[midPrev])
                newClass[cur] = newClass[prev] + 1;
            else
                newClass[cur] = newClass[prev];
        }
        return newClass;
    }

    private int[] computeCharClasses(String s, int[] order) {
        int ls = s.length();
        int[] cls = new int[ls];
        cls[order[0]] = 0;
        for (int i = 1; i < ls; i++) {
            if (s.charAt(order[i]) != s.charAt(order[i - 1])) {
                cls[order[i]] = cls[order[i - 1]] + 1;
            } else {
                cls[order[i]] = cls[order[i - 1]];
            }
        }
        return cls;
    }


    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
//        new assignment3_4.SuffixArrayLong().test();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }

    private void test() {
        String text = "AAA$";
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
        System.out.println();

        text = "GAC$";
        suffix_array = computeSuffixArray(text);
        print(suffix_array);
        System.out.println();

        text = "GAGAGAGA$";
        suffix_array = computeSuffixArray(text);
        print(suffix_array);
        System.out.println();

        text = "AACGATAGCGGTAGA$";
        suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }

    private static class EquivalenceClass implements Comparable {
        Integer value = 0;
        int size = 0;

        private EquivalenceClass() {
        }

        private void addLetter(char ltr) {
            int exp = 1;
            for (int i = 0; i < size; i++) {
                exp *= 5;
            }
            value += ltn(ltr) * exp;
            size++;
        }

        public static int ltn(char a) {
            switch (a) {
                case '$':
                    return 0;
                case 'A':
                    return 1;
                case 'C':
                    return 2;
                case 'G':
                    return 3;
                default:
                    return 4;
            }
        }

        @Override
        public int compareTo(Object o) {
            EquivalenceClass other = (EquivalenceClass) o;
            return value.compareTo(other.value);
        }

    }

}
