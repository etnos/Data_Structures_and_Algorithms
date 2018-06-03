import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
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

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();
        // write your code here

        char[] lastColArray = bwt.toCharArray();
        Arrays.sort(lastColArray);
        int textLength = bwt.length();

        // structure to keep characters total count number
        HashMap<Character, Integer> ordinals = initOrdinals();

        // init last column with char and char index
        ColumnChar[] lastCol = initLastCoulmn(bwt, ordinals);
        // just copy the last column and sort it.
        // char index should stay the same
        ColumnChar[] firstCol = initFirstColumn(lastCol);

        HashMap<Character, ArrayList<Integer>> charIndices = initColumnCharIndices(bwt, textLength);

        ColumnChar currentChar = new ColumnChar('$', 0);
        for (int i = 0; i < bwt.length(); i++) {
            currentChar = firstCol[charIndices.get(currentChar.c).get(currentChar.ordinal)];
            currentChar = lastCol[charIndices.get(currentChar.c).get(currentChar.ordinal)];
            result.append(currentChar.c);
        }

        return result.toString();
    }

    private HashMap<Character, ArrayList<Integer>> initColumnCharIndices(String bwt, int textLength) {
        HashMap<Character, ArrayList<Integer>> lastColCharIndices = new HashMap<>();
        lastColCharIndices.put('A', new ArrayList<>());
        lastColCharIndices.put('C', new ArrayList<>());
        lastColCharIndices.put('T', new ArrayList<>());
        lastColCharIndices.put('G', new ArrayList<>());
        lastColCharIndices.put('$', new ArrayList<>());
        for (int i = 0; i < textLength; i++) {
            ArrayList<Integer> indices = lastColCharIndices.get(bwt.charAt(i));
            indices.add(i);
        }
        return lastColCharIndices;
    }

    private ColumnChar[] initFirstColumn(ColumnChar[] lastCol) {
        ColumnChar[] firstCol = Arrays.copyOf(lastCol, lastCol.length);
        Arrays.sort(firstCol);
        return firstCol;
    }

    private HashMap<Character, Integer> initOrdinals() {
        HashMap<Character, Integer> ordinals = new HashMap<>();

        ordinals.put('A', 0);
        ordinals.put('C', 0);
        ordinals.put('T', 0);
        ordinals.put('G', 0);
        ordinals.put('$', 0);

        return ordinals;
    }

    private ColumnChar[] initLastCoulmn(String bwt, HashMap<Character, Integer> ordinals) {
        int textLength = bwt.length();
        ColumnChar[] lastCol = new ColumnChar[textLength];
        for (int i = 0; i < textLength; i++) {
            char nextChar = bwt.charAt(i);
            lastCol[i] = new ColumnChar(nextChar, ordinals.get(nextChar));
            ordinals.put(nextChar, ordinals.get(nextChar) + 1);
        }

        return lastCol;
    }


    private class ColumnChar implements Comparable<ColumnChar> {
        char c;
        int ordinal;

        public ColumnChar(char c, int ordinal) {
            this.c = c;
            this.ordinal = ordinal;
        }

        @Override
        public int compareTo(ColumnChar o) {
            if (this.c == o.c)
                return 0;
            return this.c < o.c ? -1 : 1;
        }

    }


    String inverseBWTSlow(String bwt) {
        StringBuilder result = new StringBuilder();
        // write your code here
        int length = bwt.length();
        char[][] matrix = new char[length][length];
        // init matrix last column
        for (int i = 0; i < length; i++) {
            matrix[i][matrix.length - 1] = bwt.charAt(i);
        }

        String[] temp = new String[length];

        for (int j = 0; j < length - 1; j++) {
            for (int i = 0; i < length; i++) {
                temp[i] = getLine(matrix, i);
            }
            Arrays.sort(temp);
            initMatrix(matrix, temp);
        }

//        printMatirx(matrix);
        // replace $
        for (int i = 1; i < length; i++) {
            result.append(matrix[0][i]);
        }
        result.append(matrix[0][0]);
        return result.toString();
    }

    private void initMatrix(char[][] matrix, String[] temp) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length(); j++) {
                matrix[i][j] = temp[i].charAt(j);
            }
        }
    }

    private String getLine(char[][] matrix, int row) {
        int column = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(matrix[row][matrix.length - 1]);
        while (column < matrix.length - 1 && matrix[row][column] > 0) {
            sb.append(matrix[row][column]);
            column++;
        }
        return sb.toString();
    }

    private void printMatirx(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + "|");
            }
            System.out.println();
        }
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
//        new InverseBWT().test();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }

    private void test() {
        String bwtText = "AC$A";
        System.out.println(inverseBWT(bwtText));

        bwtText = "AGGGAA$";
        System.out.println(inverseBWT(bwtText));

    }
}
