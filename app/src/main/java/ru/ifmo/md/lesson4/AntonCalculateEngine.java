package ru.ifmo.md.lesson4;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by anton on 01/10/14.
 */
public class AntonCalculateEngine implements CalculationEngine {
    public enum Lexem {
        OPERATOR, OPERAND, LBRACKET, RBRACKET
    }

    private static boolean isOp(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    private static boolean isDelim(char c) {
        return c == ' ' || c == '\t';
    }

    private static boolean isUnary(String op) {
        return op.equals("!-") || op.equals("!+");
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

    private static void processOp(LinkedList<Double> st, String op) throws CalculationException {
        try {
            Double r = st.removeLast();

            if (op.equals("!-")) {
                st.addLast(-r);
            } else if (op.equals("!+")) {
                st.addLast(r);
            } else {
                try {
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
                } catch (NoSuchElementException e) {
                    throw new CalculationException();
                }
            }
        } catch (NoSuchElementException e) {
            throw new CalculationException();
        }
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        Lexem lastOp = null;
        LinkedList<Double> st = new LinkedList<Double>();
        LinkedList<String> op = new LinkedList<String>();

        for (int i = 0; i < expression.length(); i++) {
            if (!isDelim(expression.charAt(i))) {
                if (expression.charAt(i) == '(') {
                    if (lastOp == Lexem.RBRACKET) {
                        // ") (" can not happen in valid expression
                        throw new CalculationException();
                    } else if (lastOp == Lexem.OPERAND) {
                        // as well as "<number> ("
                        throw new CalculationException();
                    }
                    op.addLast("(");
                    lastOp = Lexem.LBRACKET;
                } else if (expression.charAt(i) == ')') {
                    if (lastOp == null) {
                        // ")" at the beginning of the expression
                        throw new CalculationException();
                    }
                    if (lastOp == Lexem.LBRACKET) {
                        // "()" is not valid
                        throw new CalculationException();
                    }
                    try {
                        while (!op.peekLast().equals("(")) {
                            processOp(st, op.removeLast());
                        }
                    } catch (NullPointerException e) {
                        // extra ")"
                        throw new CalculationException();
                    }

                    op.removeLast();
                    lastOp = Lexem.RBRACKET;
                } else if (isOp("" + expression.charAt(i))) {
                    String curOp = "" + expression.charAt(i);

                    if (lastOp != Lexem.RBRACKET && lastOp != Lexem.OPERAND) {
                        if (curOp.equals("-")) {
                            curOp = "!-";
                        } else if (curOp.equals("+")) {
                            curOp = "!+";
                        }
                    }
                    if (!isUnary(curOp) && lastOp == null) {
                        // binary operator at the beginning of the expression
                        throw new CalculationException();
                    }
                    if (!isUnary(curOp) && lastOp != Lexem.OPERAND && lastOp != Lexem.RBRACKET) {
                        // operator after another operator or left bracket
                        throw new CalculationException();
                    }

                    while (!op.isEmpty() && (!isUnary(curOp) && priority(op.peekLast()) >= priority(curOp) || isUnary(curOp) && priority(op.peekLast()) > priority(curOp))) {
                        processOp(st, op.removeLast());
                    }

                    op.addLast(curOp);
                    lastOp = Lexem.OPERATOR;
                } else {
                    StringBuilder builder = new StringBuilder();

                    while (i < expression.length() &&
                            (Character.isDigit(expression.charAt(i))
                             || expression.charAt(i) == '.'
                             || Character.toLowerCase(expression.charAt(i)) == 'e'
                             || (expression.charAt(i) == '-' && i > 0 && Character.toLowerCase(expression.charAt(i - 1)) == 'e')))
                    {
                        builder.append(expression.charAt(i++));
                    }
                    i--;

                    String operand = builder.toString();
                    if (operand.isEmpty()) {
                        // unknown lexem
                        throw new CalculationException();
                    }
                    try {
                        Double number = Double.parseDouble(operand);

                        if (lastOp == Lexem.RBRACKET || lastOp == Lexem.OPERAND) {
                            // number after another number or rbracket
                            throw new CalculationException();
                        }

                        st.addLast(number);
                        lastOp = Lexem.OPERAND;
                    } catch (NumberFormatException e) {
                        // invalid number
                        throw new CalculationException();
                    }
                }
            }
        }

        while (!op.isEmpty()) {
            processOp(st, op.removeLast());
        }

        if (st.isEmpty()) {
            // ok, special case
            return 0.0;
        }

        return st.removeLast();
    }
}
