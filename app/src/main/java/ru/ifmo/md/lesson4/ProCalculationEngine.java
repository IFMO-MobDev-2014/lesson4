package ru.ifmo.md.lesson4;

import java.util.ArrayDeque;

/**
 * Created by gshark on 06.10.14.
 */
public class ProCalculationEngine implements CalculationEngine {

    private static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '!'
                || c == '#' || c == '~');
    }

    private static boolean isIdent(char c) {
        return (c >= '0' && c <= '9') || (c >= 'x' && c <= 'z');
    }

    private static boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean opleftAssoc(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private static int opArgumentCount(char c) {
        if (c == '!' || c == '#' || c == '~') {
            return 1;
        }
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            return 2;
        }
        return 0;
    }

    private static int opPreced(char c) {
        if (c == '!' || c == '#' || c == '~') {
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

    private static ArrayDeque shuntingYard(String input) throws CalculationException {
        ArrayDeque<String> output = new ArrayDeque<String>();
        ArrayDeque<Character> stack = new ArrayDeque<Character>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isIdent(c)) {
                if (isDigit(c)) {
                    double cur = c - '0';
                    i++;
                    while (i < input.length() && isDigit(input.charAt(i))) {
                        cur = cur * 10 + (input.charAt(i) - '0');
                        i++;
                    }
                    if (i < input.length() && input.charAt(i) == '.') {
                        i++;
                        double q = 10;
                        while (i < input.length() && isDigit(input.charAt(i))) {
                            cur = cur + (double)(input.charAt(i) - '0') / q;
                            q *= 10;
                            i++;
                        }
                    }
                    if (i < input.length() && input.charAt(i) == 'E') {
                        int step = 0;
                        i++;
                        while(i < input.length() && isDigit(input.charAt(i))) {
                            step = step * 10 + (input.charAt(i) - '0');
                            i++;
                        }
                        for (int k = 0; k < step; k++) {
                            cur *= 10;
                        }
                    }
                    i--;
                    output.addLast(String.valueOf(cur));
                } else {
                    output.addLast(String.valueOf(c));
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty()) {
                    char sc;
                    sc = (char) stack.peekLast();
                    if (isOperator(sc)
                            && (opleftAssoc(c) && (opPreced(c) <= opPreced(sc)))
                            || (!opleftAssoc(c) && (opPreced(c) < opPreced(sc)))) {
                        output.addLast(String.valueOf(sc));
                        stack.removeLast();
                    } else {
                        break;
                    }
                }
                stack.addLast(c);
            } else if (c == '(') {
                stack.addLast(c);
            } else if (c == ')') {
                boolean pe = true;
                while (!stack.isEmpty()) {
                    char sc = (char) stack.peekLast();
                    if (sc == '(') {
                        pe = false;
                        break;
                    } else {
                        output.addLast(String.valueOf(sc));
                        stack.removeLast();
                    }
                }
                if (pe) {
                    throw new CalculationException("PE: '(' not found");
                }
                stack.removeLast();
            } else if (c == '.') {
                throw new CalculationException("Wrong number");
            } else {
                throw new CalculationException("PE: unknown token");
            }
// }
        }
        while (!stack.isEmpty()) {
            char sc = (char) stack.removeLast();
            if (sc == '(' || sc == ')') {
                throw new CalculationException("PE: bad brackets");
            }
            output.addLast(String.valueOf(sc));
        }
        return output;
    }
    private double execute(ArrayDeque input) throws CalculationException {
        ArrayDeque stack = new ArrayDeque();
        while (!input.isEmpty()) {
            String str = (String) input.removeFirst();
            char c = str.charAt(0);
            if (isIdent(c)) {
                    stack.addLast(Double.parseDouble(str));
            } else if (isOperator(c)) {
                int nargs = opArgumentCount(c);
                if (stack.size() < nargs) {
                    throw new CalculationException("Error: not enough arguments");
                }
                if (c == '!') {
                    double once = (Double) stack.removeLast();
                    stack.addLast(-once);
                } else if (c == '+') {
                    double first = (Double) stack.removeLast();
                    double second = (Double) stack.removeLast();
                    stack.addLast(first + second);
                } else if (c == '-') {
                    double second = (Double) stack.removeLast();
                    double first = (Double) stack.removeLast();
                    stack.addLast(first - second);
                } else if (c == '*') {
                    double first = (Double) stack.removeLast();
                    double second = (Double) stack.removeLast();
                    stack.addLast(first * second);
                } else if (c == '/') {
                    double second = (Double) stack.removeLast();
                    double first = (Double) stack.removeLast();
                    if (Double.valueOf(0).equals(second)) {
                        throw new CalculationException("Error: Dividion by zero");
                    }
                    stack.addLast(first / second);
                }
            }
        }
        assert (stack.size() == 1);
        return (Double) stack.removeLast();
    }
    private String doGood(String expression) throws CalculationException {
        char[] arrexpr = expression.toCharArray();
        int whitespaces = 0;
        for (int i = 0; i < arrexpr.length; i++) {
            if (Character.isWhitespace(arrexpr[i])) {
                whitespaces++;
            }
        }
        char[] newexpr = new char[arrexpr.length - whitespaces];
        int j = 0;
        for (int i = 0; i < arrexpr.length; i++) {
            if (!Character.isWhitespace(arrexpr[i])) {
                newexpr[j++] = arrexpr[i];
            }
        }
        if (newexpr[0] == '-') {
            newexpr[0] = '!';
        }
        for (int i = 1; i < newexpr.length; i++) {
            if (newexpr[i] == '-' && (isOperator(newexpr[i - 1]) || newexpr[i - 1] == '(')) {
                newexpr[i] = '!';
            }
        }
        for (int i = 1; i < newexpr.length; i++) {
            if (newexpr[i - 1] == '(' && newexpr[i] == ')') {
                throw new CalculationException("Empty brackets");
            }
            if (newexpr[i] == '(' && (isDigit(newexpr[i - 1]) || newexpr[i - 1] == '.' || newexpr[i - 1] == ')')) {
                throw new CalculationException("Impossible possision for '('");
            }
            if (newexpr[i] == ')' && !isDigit(newexpr[i - 1]) && newexpr[i - 1] != ')') {
                throw new CalculationException("Impossible possision for ')'");
            }
            if(newexpr[i - 1] == ')' && isDigit(newexpr[i])) {
                throw new CalculationException("Wrong symbol after ')'");
            }
        }
        return String.valueOf(newexpr);
    }

    public double calculate(String expression) throws CalculationException {
        return execute(shuntingYard(doGood(expression)));
    }

 }
