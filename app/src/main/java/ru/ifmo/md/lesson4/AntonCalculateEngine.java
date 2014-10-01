package ru.ifmo.md.lesson4;

import java.util.LinkedList;

/**
 * Created by anton on 01/10/14.
 */
public class AntonCalculateEngine implements CalculationEngine {
    private static boolean isOp(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    private static boolean isDelim(char c) {
        return c == ' ' || c == '\t';
    }

    private static boolean isUnary(String op) {
        return op.equals("^");
    }

    private static int priority(String op) {
        if (isUnary(op)) {
            return 4;
        } else if (op.equals("*") || op.equals("/")) {
            return 2;
        } else if (op.equals("+") || op.equals("-")) {
            return 1;
        } else {
            return -1;
        }
    }

    private static void processOp(LinkedList<Double> st, String op) {
        Double r = st.removeLast();
        if (op.equals("^")) {
            st.addLast(-r);
        } else {
            Double l = st.removeLast();
            if (op.equals("+")) {
                st.addLast(l + r);
            } else if (op.equals("-")) {
                st.addLast(l - r);
            } else if (op.equals("*")) {
                st.addLast(l * r);
            } else if (op.equals("/")) {
                st.addLast(l / r);
            }
        }
    }

    @Override
    public double calculate(String expression) {
        boolean mayUnaryMinus = true;
        LinkedList<Double> st = new LinkedList<Double>();
        LinkedList<String> op = new LinkedList<String>();
        for (int i = 0; i < expression.length(); i++) {
            if (!isDelim(expression.charAt(i))) {
                if (expression.charAt(i) == '(') {
                    op.addLast("(");
                    mayUnaryMinus = true;
                } else if (expression.charAt(i) == ')') {
                    while (!op.peekLast().equals("(")) {
                        processOp(st, op.removeLast());
                    }
                    op.removeLast();
                    mayUnaryMinus = false;
                } else if (isOp("" + expression.charAt(i))) {
                    String curOp = "" + expression.charAt(i);
                    if (mayUnaryMinus && curOp.equals("-")) curOp = "^";
                    while (!op.isEmpty() && (!isUnary(curOp) && priority(op.peekLast()) >= priority(curOp) || isUnary(curOp) && priority(op.peekLast()) > priority(curOp))) {
                        processOp(st, op.removeLast());
                    }
                    op.addLast(curOp);
                    mayUnaryMinus = true;
                } else {
                    StringBuilder builder = new StringBuilder();
                    while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                        builder.append(expression.charAt(i++));
                    }
                    i--;
                    String operand = builder.toString();
                    Double number = Double.parseDouble(operand);
                    st.addLast(number);
                    mayUnaryMinus = false;
                }
            }
        }
        while (!op.isEmpty()) {
            processOp(st, op.removeLast());
        }
        return st.removeLast();
    }
}
