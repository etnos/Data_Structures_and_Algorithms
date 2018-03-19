package assignment1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        System.out.println(processBrackets(text));
//        test();
    }

    private static String processBrackets(String text) {
        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
                opening_brackets_stack.push(new Bracket(next, position));
            }

            if (next == ')' || next == ']' || next == '}') {
                // Process closing bracket, write your code here
                if (!opening_brackets_stack.isEmpty() && opening_brackets_stack.peek().Match(next)) {
                    opening_brackets_stack.pop();
                } else {
                    opening_brackets_stack.push(new Bracket(next, position));
                    break;
                }
            }
        }

        if (opening_brackets_stack.isEmpty()) {
            return "Success";
        } else {
            return String.valueOf(opening_brackets_stack.peek().position + 1);
        }
    }


    private static void test() {
        testCase("[]", "Success");
        testCase("{}[]", "Success");
        testCase("[()]", "Success");
        testCase("(())", "Success");
        testCase("{[]}()", "Success");
        testCase("foo(bar);", "Success");
        testCase("{", "1");
        testCase("}", "1");
        testCase("(()()", "1");
        testCase("{[}", "3");
        testCase("foo(bar[i);", "10");

    }

    private static void testCase(String text, String resultExpected) {
        String resultLocal = processBrackets(text);
        if (!resultLocal.equals(resultExpected)) {
            System.out.println("WRONG " + resultLocal + " text: " + text);
            System.exit(0);
        }
    }
}
