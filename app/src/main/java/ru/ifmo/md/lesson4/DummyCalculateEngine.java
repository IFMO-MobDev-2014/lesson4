package ru.ifmo.md.lesson4;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by Шолохов on 18.01.2014.
 */

public class DummyCalculateEngine implements CalculationEngine {

    public final int OPR = 10;
    public final int OPD = 11;
    public final int LBR = 12;
    public final int RBR = 13;

    private static boolean isBinaryOperator(String s) {
         if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
             return true;
         } else return false;
    }

    private static boolean isUnaryOperator(String s) {
        if (s.equals("!-") || s.equals("!+")) {
            return true;
        } else return false;
    }

    private static boolean isSpace(char c) {
        if (c == ' ' || c == '\t') {
            return true;
        } else return false;
    }

    private static int priority(String s) {
        if (isUnaryOperator(s)) {
            return 4;
        } else if (s.equals("*") || s.equals("/")) {
            return 2;
        } else if (s.equals("+") || s.equals("-")) {
            return 1;
        } else {
            return -1;
        }
    }

    private int last = 0;

    @Override
    public double calculate(String expression) throws CalculationException {

        if (expression == null) {
            throw new CalculationException();
        }

        LinkedList<String> operators = new LinkedList<String>();
        LinkedList<Double> store = new LinkedList<Double>();

        for (int i = 0; i < expression.length(); i++) {
            if (!isSpace(expression.charAt(i))) {
                if (expression.charAt(i) == '(') {
                    if (last == RBR) {
                        throw new CalculationException();
                    } else if (last == OPD) {
                        throw new CalculationException();
                    }
                    operators.addLast("(");
                    last = LBR;
                } else if (expression.charAt(i) == ')') {
                    if (last == 0) {
                        throw new CalculationException();
                    }
                    if (last == LBR) {
                        throw new CalculationException();
                    }
                    try {
                        while (!operators.peekLast().equals("(")) {
                            processOperators(store, operators.removeLast());
                        }
                    } catch (NullPointerException e) {
                        throw new CalculationException();
                    }

                    operators.removeLast();
                    last = RBR;
                } else if (isBinaryOperator("" + expression.charAt(i))) {
                    String curOp = "" + expression.charAt(i);

                    if (last != RBR && last != OPD) {
                        if (curOp.equals("-")) {
                            curOp = "!-";
                        } else if (curOp.equals("+")) {
                            curOp = "!+";
                        }
                    }
                    if (!isUnaryOperator(curOp) && last == 0) {
                        throw new CalculationException();
                    }
                    if (!isUnaryOperator(curOp) && last != OPD && last != RBR) {
                        throw new CalculationException();
                    }

                    while (!operators.isEmpty() && (!isUnaryOperator(curOp) && priority(operators.peekLast()) >= priority(curOp) || isUnaryOperator(curOp) && priority(operators.peekLast()) > priority(curOp))) {
                        processOperators(store, operators.removeLast());
                    }

                    operators.addLast(curOp);
                    last = OPR;
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
                        throw new CalculationException();
                    }
                    try {
                        Double number = Double.parseDouble(operand);

                        if (last == RBR || last == OPD) {
                            throw new CalculationException();
                        }

                        store.addLast(number);
                        last = OPD;
                    } catch (NumberFormatException e) {
                        throw new CalculationException();
                    }
                }
            }
        }

        while (!operators.isEmpty()) {
            processOperators(store, operators.removeLast());
        }

        if (store.isEmpty()) {
            throw new CalculationException();
        } else {
            return store.removeLast();
        }
    }

    private static void processOperators(LinkedList<Double> store, String s) throws CalculationException {
        try {
            Double r = store.removeLast();

            if (s.equals("!+")) {
                store.addLast(r);
            } else if (s.equals("!-")) {
                store.addLast(-r);
            } else {
                try {
                    Double l = store.removeLast();

                    if (s.equals("+")) {
                        store.addLast(l + r);
                    } else if (s.equals("-")) {
                        store.addLast(l - r);
                    } else if (s.equals("*")) {
                        store.addLast(l * r);
                    } else if (s.equals("/")) {
                        store.addLast(l / r);
                    }
                } catch (NoSuchElementException e) {
                    throw new CalculationException();
                }
            }
        } catch (NoSuchElementException e) {
            throw new CalculationException();
        }
    }
}
