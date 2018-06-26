package assignment2;

import java.io.*;
import java.util.*;

class Answer {
    int anst;
    double[] ansx;

    Answer(int m) {
        ansx = new double[m];
        anst = 0;
    }

    public void noAnswer() {
        anst = -1;
    }

    public void infiniteAnswer() {
        anst = 1;
    }

    public void finalize() {
        for (int i = 0; i < ansx.length; i++) {
            if (ansx[i] < 0.000001)
                ansx[i] = 0;
        }
    }
}

enum Comparator {
    LESSOREQUAL, LESSTHAN, EQUAL, GREATERTHAN, GREATEROREQUAL
}

class PositionDiet {
    PositionDiet(int column, int row) {
        this.column = column;
        this.row = row;
    }

    int column;
    int row;
}

class Sentence {
    double[] coefficients;
    double constant;
    Comparator comp;
    Comparator[] compVals = Comparator.values();

    public Sentence(double[] coefficients, double constant, Comparator comp) {
        this.coefficients = coefficients;
        this.constant = constant;
        this.comp = comp;
    }

    public Sentence(int size, Comparator comp) {
        this.coefficients = new double[size];
        this.comp = comp;
    }

    public void scale(double scaleAmount) {
        for (double c : coefficients) {
            c *= scaleAmount;
        }
        constant *= scaleAmount;

        if (scaleAmount < 0) {
            int newComp = (((comp.ordinal() - 2) * -1) + 2);
            comp = compVals[newComp];
        }
    }

    public void setCoefficient(int location, double value) {
        this.coefficients[location] = value;
        int x = 0;
    }

    public void setConstant(double value) {
        constant = value;
    }

    public boolean testValues(double[] values) {
        double total = 0;
        for (int i = 0; i < coefficients.length; i++) {
            total += values[i] * coefficients[i];
        }
        switch (comp) {
            case LESSOREQUAL:
                return total <= constant;
            case LESSTHAN:
                return total < constant;
            case GREATEROREQUAL:
                return total >= constant;
            case GREATERTHAN:
                return total > constant;
            default:
                return total == constant;
        }
    }

    public static double[] findVertex(ArrayList<Sentence> sentences) {
        int size = sentences.get(0).coefficients.length;
        double[][] a = new double[sentences.size()][size];
        double[] b = new double[sentences.size()];
        double[] answer = new double[size];

        for (int i = 0; i < sentences.size(); i++) {
            Sentence sentence = sentences.get(i);
            if (sentence.coefficients.length != size)
                return new double[0];   //no answer if they're not all the same size
            a[i] = Arrays.copyOf(sentence.coefficients, sentence.coefficients.length);
            b[i] = sentence.constant;
        }
        double[] x = SolveEquation(a, b);
        return x;

    }

    static PositionDiet SelectPivotElement(double a[][], boolean used_rows[], boolean used_columns[]) {
        // This algorithm selects the first free element.
        // You'll need to improve it to pass the problem.
        PositionDiet pivot_element = new PositionDiet(0, 0);
        while (used_rows[pivot_element.row])
            ++pivot_element.row;
        while (used_columns[pivot_element.column])
            ++pivot_element.column;
        PositionDiet firstFree = pivot_element;
        while (a[pivot_element.row][pivot_element.column] == 0) {
            ++pivot_element.row;
            if (pivot_element.row >= a.length) {
                pivot_element.row = firstFree.row;
                pivot_element.column++;
            }
            if (pivot_element.row >= a.length || pivot_element.column > a[pivot_element.row].length)
                return new PositionDiet(-1, -1);
        }
        return pivot_element;
    }

    static void SwapLines(double a[][], double b[], boolean used_rows[], PositionDiet pivot_element) {
        int size = a.length;

        for (int column = 0; column < size; ++column) {
            double tmpa = a[pivot_element.column][column];
            a[pivot_element.column][column] = a[pivot_element.row][column];
            a[pivot_element.row][column] = tmpa;
        }

        double tmpb = b[pivot_element.column];
        b[pivot_element.column] = b[pivot_element.row];
        b[pivot_element.row] = tmpb;

        boolean tmpu = used_rows[pivot_element.column];
        used_rows[pivot_element.column] = used_rows[pivot_element.row];
        used_rows[pivot_element.row] = tmpu;

        pivot_element.row = pivot_element.column;
    }

    static void ProcessPivotElement(double a[][], double b[], PositionDiet pivot_element) {
        int size = a.length;
        Double currentCell = a[pivot_element.row][pivot_element.column];
        for (int i = 0; i < size; i++) {
            a[pivot_element.row][i] = a[pivot_element.row][i] / currentCell;
        }
        b[pivot_element.row] = b[pivot_element.row] / currentCell;
        for (int i = 0; i < size; i++) {
            if (i != pivot_element.row) {
                double scale = a[i][pivot_element.column];
                for (int j = 0; j < size; j++) {
                    a[i][j] -= a[pivot_element.row][j] * scale;
                }
                b[i] -= b[pivot_element.row] * scale;
            }
        }
    }

    static void MarkPivotElementUsed(PositionDiet pivot_element, boolean used_rows[], boolean used_columns[]) {
        used_rows[pivot_element.row] = true;
        used_columns[pivot_element.column] = true;
    }

    static double[] SolveEquation(double[][] a, double[] b) {
        int size = a.length;
        boolean[] used_columns = new boolean[size];
        boolean[] used_rows = new boolean[size];
        for (int step = 0; step < size; ++step) {
            PositionDiet pivot_element = SelectPivotElement(a, used_rows, used_columns);
            if (pivot_element.row < 0)
                return new double[0];
            SwapLines(a, b, used_rows, pivot_element);
            ProcessPivotElement(a, b, pivot_element);
            MarkPivotElementUsed(pivot_element, used_rows, used_columns);
        }

        return b;
    }

}


public class Diet {
    boolean debug = false;
    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    double evaluateExpression(double[] coefficients, double[] variableValues) {
        double answer = 0;
        if (coefficients.length != variableValues.length)
            return Double.MIN_VALUE;
        for (int i = 0; i < coefficients.length; i++) {
            answer += coefficients[i] * variableValues[i];
        }
        return answer;
    }

    Answer solveDietProblem(int n, int m, Sentence[] inequalities, double[] c) {
        Answer a = new Answer(m);
        double maxPleasure = Double.NEGATIVE_INFINITY;
        ArrayList<ArrayList<Sentence>> sentenceSets = new ArrayList<>();
        Sentence[][] sentenceSetArray = makeSubSets(inequalities, m);
        for (int i = 0; i < sentenceSetArray.length; i++) {
            sentenceSets.add(new ArrayList<Sentence>(Arrays.asList(sentenceSetArray[i])));
        }
        boolean infiniteAnswer = false;
        for (ArrayList<Sentence> set : sentenceSets) {
            double[] solution = Sentence.findVertex(set);
            if (debug) {
                System.out.println("Solutions");
                for (int i = 0; i < solution.length; i++) {
                    System.out.printf("%f ", solution[i]);
                }
                System.out.println();
            }
            //Make sure that this vertex works on all the sentences not in the set
            if (solution.length > 0) {
                boolean isInPolytype = true;
                for (Sentence s : inequalities) {
                    if (!set.contains(s)) {
                        if (!s.testValues(solution)) {
                            isInPolytype = false;
                            break;
                        }
                    }
                }

                if (isInPolytype) {
                    double pleasureAmount = evaluateExpression(c, solution);
                    boolean test = pleasureAmount > maxPleasure;
                    if (test) {
                        maxPleasure = pleasureAmount;
                        a.ansx = solution;
                        infiniteAnswer = false;
                        for (int k = 0; k < set.size(); k++) {
                            if (set.get(k).constant >= 1000000000) {
                                infiniteAnswer = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (maxPleasure == Double.NEGATIVE_INFINITY) {
            a.noAnswer();
            return a;
        } else if (infiniteAnswer) {
            a.infiniteAnswer();
            return a;
        }
        a.finalize();
        return a;
    }

    /**
     * Gets all sets of sentences of size m
     *
     * @return
     */
    void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Sentence[] inequalities = new Sentence[n + m + 1];
        for (int i = 0; i < n; i++)
            inequalities[i] = new Sentence(m, Comparator.LESSOREQUAL);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                inequalities[i].setCoefficient(j, nextInt());
            }
        }
        for (int i = 0; i < n; i++) {
            inequalities[i].setConstant(nextInt());
        }

        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = nextInt();
        }
        for (int i = n; i < inequalities.length - 1; i++) {
            inequalities[i] = new Sentence(m, Comparator.GREATEROREQUAL);
            for (int j = 0; j < m; j++) {
                if (j == i - n) {
                    inequalities[i].setCoefficient(j, 1);
                } else {
                    inequalities[i].setCoefficient(j, 0.000000000000);
                }
            }
            inequalities[i].setConstant(0.000000000000);
        }
        int lastIneq = inequalities.length - 1;
        inequalities[lastIneq] = new Sentence(m, Comparator.LESSOREQUAL);
        for (int i = 0; i < m; i++) {
            inequalities[lastIneq].setCoefficient(i, 1);
            inequalities[lastIneq].setConstant(1000000000);
        }
        Answer ans = solveDietProblem(n + m, m, inequalities, c);
        if (ans.anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (ans.anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ans.ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (ans.anst == 1) {
            out.printf("Infinity\n");
            return;
        }
    }

    void solveTest() throws IOException {
        test1();
        test2();
        test3();
    }

    private void test1() {
        int n = 3;
        int m = 2;
        Sentence[] inequalities = new Sentence[n + m + 1];
        for (int i = 0; i < n; i++)
            inequalities[i] = new Sentence(m, Comparator.LESSOREQUAL);
        int[][] coefficientsOfTheLinearInequalities = new int[][]{{-1, -1}, {1, 0}, {0, 1}};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                inequalities[i].setCoefficient(j, coefficientsOfTheLinearInequalities[i][j]);
            }
        }

        int[] constants = new int[]{-1, 2, 2};
        for (int i = 0; i < n; i++) {
            inequalities[i].setConstant(constants[i]);
        }

        double[] c = new double[m];
        int[] pleasureForConsuming = new int[]{-1, 2};
        for (int i = 0; i < m; i++) {
            c[i] = pleasureForConsuming[i];
        }
        for (int i = n; i < inequalities.length - 1; i++) {
            inequalities[i] = new Sentence(m, Comparator.GREATEROREQUAL);
            for (int j = 0; j < m; j++) {
                if (j == i - n) {
                    inequalities[i].setCoefficient(j, 1);
                } else {
                    inequalities[i].setCoefficient(j, 0.000000000000);
                }
            }
            inequalities[i].setConstant(0.000000000000);
        }
        int lastIneq = inequalities.length - 1;
        inequalities[lastIneq] = new Sentence(m, Comparator.LESSOREQUAL);
        for (int i = 0; i < m; i++) {
            inequalities[lastIneq].setCoefficient(i, 1);
            inequalities[lastIneq].setConstant(1000000000);
        }
        Answer ans = solveDietProblem(n + m, m, inequalities, c);
        if (ans.anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (ans.anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ans.ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (ans.anst == 1) {
            out.printf("Infinity\n");
            return;
        }
    }

    private void test2() {
        int n = 2;
        int m = 2;
        Sentence[] inequalities = new Sentence[n + m + 1];
        for (int i = 0; i < n; i++)
            inequalities[i] = new Sentence(m, Comparator.LESSOREQUAL);
        int[][] coefficientsOfTheLinearInequalities = new int[][]{{1, 1}, {-1, -1}};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                inequalities[i].setCoefficient(j, coefficientsOfTheLinearInequalities[i][j]);
            }
        }

        int[] constants = new int[]{1, -2};
        for (int i = 0; i < n; i++) {
            inequalities[i].setConstant(constants[i]);
        }

        double[] c = new double[m];
        int[] pleasureForConsuming = new int[]{1, 1};
        for (int i = 0; i < m; i++) {
            c[i] = pleasureForConsuming[i];
        }
        for (int i = n; i < inequalities.length - 1; i++) {
            inequalities[i] = new Sentence(m, Comparator.GREATEROREQUAL);
            for (int j = 0; j < m; j++) {
                if (j == i - n) {
                    inequalities[i].setCoefficient(j, 1);
                } else {
                    inequalities[i].setCoefficient(j, 0.000000000000);
                }
            }
            inequalities[i].setConstant(0.000000000000);
        }
        int lastIneq = inequalities.length - 1;
        inequalities[lastIneq] = new Sentence(m, Comparator.LESSOREQUAL);
        for (int i = 0; i < m; i++) {
            inequalities[lastIneq].setCoefficient(i, 1);
            inequalities[lastIneq].setConstant(1000000000);
        }
        Answer ans = solveDietProblem(n + m, m, inequalities, c);
        if (ans.anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (ans.anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ans.ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (ans.anst == 1) {
            out.printf("Infinity\n");
            return;
        }
    }

    private void test3() {
        int n = 1;
        int m = 3;
        Sentence[] inequalities = new Sentence[n + m + 1];
        for (int i = 0; i < n; i++)
            inequalities[i] = new Sentence(m, Comparator.LESSOREQUAL);
        int[][] coefficientsOfTheLinearInequalities = new int[][]{{0, 1, 1}};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                inequalities[i].setCoefficient(j, coefficientsOfTheLinearInequalities[i][j]);
            }
        }

        int[] constants = new int[]{3};
        for (int i = 0; i < n; i++) {
            inequalities[i].setConstant(constants[i]);
        }

        double[] c = new double[m];
        int[] pleasureForConsuming = new int[]{1, 1, 1};
        for (int i = 0; i < m; i++) {
            c[i] = pleasureForConsuming[i];
        }
        for (int i = n; i < inequalities.length - 1; i++) {
            inequalities[i] = new Sentence(m, Comparator.GREATEROREQUAL);
            for (int j = 0; j < m; j++) {
                if (j == i - n) {
                    inequalities[i].setCoefficient(j, 1);
                } else {
                    inequalities[i].setCoefficient(j, 0.000000000000);
                }
            }
            inequalities[i].setConstant(0.000000000000);
        }
        int lastIneq = inequalities.length - 1;
        inequalities[lastIneq] = new Sentence(m, Comparator.LESSOREQUAL);
        for (int i = 0; i < m; i++) {
            inequalities[lastIneq].setCoefficient(i, 1);
            inequalities[lastIneq].setConstant(1000000000);
        }
        Answer ans = solveDietProblem(n + m, m, inequalities, c);
        if (ans.anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (ans.anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ans.ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (ans.anst == 1) {
            out.printf("Infinity\n");
            return;
        }
    }

    Diet() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    Diet(String test) throws IOException {
        out = new PrintWriter(System.out);
        solveTest();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Diet();
//        new assignment2.Diet("this is test run");
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    private class Grid {
        int s; //size
        int c; //max 1s per column
        int n;   //width of array
        int m;   //number of 1's
        boolean[][] arr;
        int[] columnCount;

        public Grid(int n, int m) {
            this.n = n;
            this.m = m;
            this.s = combinatorial(n, m);
            this.c = combinatorial(n - 1, m - 1);
            this.arr = new boolean[s][n];
            this.columnCount = new int[n];
            Arrays.fill(this.columnCount, 0);
        }
    }

    private Sentence[][] makeSubSets(Sentence[] set, int m) {
        Grid mainGrid = new Grid(set.length, m);
        mainGrid = fillColumns(mainGrid, set.length, m, mainGrid.s, 0, 0);
        boolean[][] arr = mainGrid.arr;
        Sentence[][] superSet = new Sentence[arr.length][m];
        for (int i = 0; i < superSet.length; i++) {
            int setPlace = 0;
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j]) {

                    superSet[i][setPlace] = set[j];

                    setPlace++;
                }
            }
        }
        return superSet;
    }

    private Grid fillColumns(Grid mainGrid, int n, int m, int s, int firstCol, int firstRow) { //[tk] add int firstRow
        int[] activeRows = new int[s];
        for (int i = 0; i < s; i++) {
            activeRows[i] = i + firstRow;
        }
        int[] activeCols = new int[n];
        for (int i = 0; i < activeCols.length; i++) {
            activeCols[i] = i + firstCol;
        }
        int c = combinatorial(n - 1, m - 1);
        if (n < m || s == 0) {
            return mainGrid;
        }
        if (m == 1) {
            for (int i = 0; i < n; i++) {

                mainGrid.arr[activeRows[i]][activeCols[i]] = true;

            }
            return mainGrid;
        }
        for (int i = 0; i < c; i++) {
            mainGrid.arr[activeRows[i]][activeCols[0]] = true;
        }
        mainGrid = fillColumns(mainGrid, n - 1, m - 1, c, firstCol + 1, firstRow); //[tk] pass firstrow
        mainGrid = fillColumns(mainGrid, n - 1, m, s - c, firstCol + 1, firstRow + c); // [k] pass firstrow
        return mainGrid;
    }

    private int combinatorial(int n, int m) {
        ArrayList<Integer> numerator = new ArrayList<>();
        ArrayList<Integer> denominator = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            numerator.add(i);
        }
        for (int i = 2; i <= m; i++) {
            if (numerator.contains(i))
                numerator.remove(numerator.indexOf(i));
            else
                denominator.add(i);
        }
        for (int i = 2; i <= n - m; i++) {
            if (numerator.contains(i))
                numerator.remove(numerator.indexOf(i));
            else
                denominator.add(i);
        }
        for (int i = 0; i < denominator.size(); i++)
            for (int j = 0; j < numerator.size(); j++) {
                if (denominator.size() <= i)
                    break;
                try {
                    if (numerator.get(j) % denominator.get(i) == 0) {
                        numerator.set(j, numerator.get(j) / denominator.get(i));
                        denominator.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e);
                }
            }
        int c = 1;
        for (int i = 0; i < numerator.size(); i++) {
            c *= numerator.get(i);
        }
        for (int i = 0; i < denominator.size(); i++) {
            c /= denominator.get(i);
        }
        return c;
    }
}

