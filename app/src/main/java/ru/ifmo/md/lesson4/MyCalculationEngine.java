package ru.ifmo.md.lesson4;

import android.util.Log;

import java.util.ArrayDeque;

public class MyCalculationEngine implements CalculationEngine {

    boolean emptyInputString = false;
    boolean emptyBrackets = false;

    @Override
    public double calculate(String expression) throws CalculationException {
        return execute(toStack(toNormalString(expression)));
    }

    private boolean isOperation(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == 'u' || c == 'p');
    }

    private boolean isIdent(char c) {
        return (c >= '0' && c <= '9') || (c == '.') || (c == 'E') || (c >= 'a' && c <= 'z' && c != 'u' && c != 'p') || (c >= 'A' && c <= 'Z');
    }

    private boolean leftAssoc(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private int numberOfArguments(char c) {
        if (c == 'u' || c == 'p') {
            return 1;
        }
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            return 2;
        }
        return 0;
    }

    private int operationPriority(char c) {
        if (c == 'u' || c == 'p') {
            return 3;
        }
        if (c == '*' || c == '/') {
            return 2;
        }
        if (c == '+' || c == '-') {
            return 1;
        }
        return 0;
    }

    private Double execute(ArrayDeque<String> input) throws CalculationException{
        ArrayDeque<Double> stack = new ArrayDeque<Double>();
        if (input.isEmpty() && emptyInputString) {
            return 0.0;
        }
        while (!input.isEmpty()) {
            String str = input.removeFirst();
            char c = str.charAt(0);
            if (isIdent(c)) {
                try {
                    stack.addLast(Double.parseDouble(str));
                } catch (Exception e) {
                    throw new CalculationException("Can't parse " + str);
                }
            } else if (isOperation(c)) {
                int args = numberOfArguments(c);
                if (stack.size() < args) {
                    if (c == 'p') {
                        throw new CalculationException("Not enough arguments for operation unary plus");
                    } else if (c == 'u') {
                        throw new CalculationException("Not enough arguments for operation unary minus");
                    } else {
                        throw new CalculationException("Not enough arguments for operation " + c);
                    }
                }
                if (c == 'u') {
                    Double e = stack.removeLast();
                    stack.addLast(-e);
                } else if (c == 'p') {
                    Double e = stack.removeLast();
                    stack.addLast(e);
                } else if (c == '+') {
                    Double first = stack.removeLast();
                    Double second = stack.removeLast();
                    stack.addLast(first + second);
                } else if (c == '-') {
                    Double second = stack.removeLast();
                    Double first = stack.removeLast();
                    stack.addLast(first - second);
                } else if (c == '*') {
                    Double first = stack.removeLast();
                    Double second = stack.removeLast();
                    stack.addLast(first * second);
                } else if (c == '/') {
                    Double second = stack.removeLast();
                    Double first =  stack.removeLast();
                    stack.addLast(first / second);
                }

            }
        }
        if (stack.size() != 1) {
            throw new CalculationException("Bad input");
        }
        return stack.removeLast();
    }

    private ArrayDeque<String> toStack(String input) throws CalculationException {
        ArrayDeque<String> stackIdent = new ArrayDeque<String>();
        ArrayDeque<Character> stackOperation = new ArrayDeque<Character>();
        if (input.length() == 0) {
            return stackIdent;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isIdent(c) || (i > 0 && c == '-' && input.charAt(i - 1) == 'E')) {
                if (emptyBrackets) {
                    emptyBrackets = false;
                }
                int j = i;
                while (isIdent(c) || (j > 0 && c == '-' && input.charAt(j - 1) == 'E')) {
                    j++;
                    if (j >= input.length()) {
                        break;
                    }
                    c = input.charAt(j);
                }
                String str = input.substring(i, j);
                stackIdent.addLast(str);
                i = j - 1;
            } else if (isOperation(c)) {
                boolean flag = true;
                while (flag && !stackOperation.isEmpty()) {
                    char sc = stackOperation.peekLast();
                    if (leftAssoc(c) && (operationPriority(c) <= operationPriority(sc)) || (!leftAssoc(c) && (operationPriority(c) < operationPriority(sc)))) {
                        stackIdent.addLast(String.valueOf(sc));
                        stackOperation.removeLast();
                    } else {
                        flag = false;
                    }
                }
                stackOperation.addLast(c);
            } else if (c == '(') {
                stackOperation.addLast(c);
                emptyBrackets = true;
            } else if (c == ')') {
                if (stackOperation.isEmpty()) {
                    throw new CalculationException("Mismatched open parenthesis");
                }
                char c1 = stackOperation.peekLast();
                if (c1 == '(' && emptyBrackets) {
                    throw new CalculationException("Empty expression in brackets");
                }
                boolean err = true;
                boolean flag = true;
                while (flag && !stackOperation.isEmpty()) {
                    char sc = stackOperation.peekLast();
                    if (sc == '(') {
                        err = false;
                        flag = false;
                    } else {
                        stackIdent.addLast(String.valueOf(sc));
                        stackOperation.removeLast();
                    }
                }
                if (err) {
                    throw new CalculationException("Mismatched open parenthesis");
                }
                stackOperation.removeLast();
            }
        }
        while (!stackOperation.isEmpty()) {
            char sc = stackOperation.removeLast();
            if (sc == '(' || sc == ')') {
                throw new CalculationException("Mismatched close parenthesis");
            }
            stackIdent.addLast(String.valueOf(sc));
        }
        return stackIdent;
    }

    private String toNormalString(String expression) {
        if (expression.equals("")) {
            emptyInputString = true;
            return expression;
        }
        char[] expr = expression.toCharArray();
        int whitespaces = 0;
        for (int i = 0; i < expr.length; i++) {
            if (Character.isWhitespace(expr[i])) {
                whitespaces++;
            }
        }
        char[] newExpression = new char[expr.length - whitespaces];
        int j = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (!Character.isWhitespace(expr[i])) {
                newExpression[j++] = expr[i];
            }
        }
        if (newExpression[0] == '-') {
            newExpression[0] = 'u';
        }
        if (newExpression[0] == '+') {
            newExpression[0] = 'p';
        }
        for (int i = 1; i < newExpression.length; i++) {
            if (newExpression[i] == '-' && (isOperation(newExpression[i - 1]) || newExpression[i - 1] == '(')) {
                newExpression[i] = 'u';
            }
            if (newExpression[i] == '+' && (isOperation(newExpression[i - 1]) || newExpression[i - 1] == '(')) {
                newExpression[i] = 'p';
            }
        }
        return String.valueOf(newExpression);
    }
}
