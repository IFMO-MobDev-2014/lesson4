package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by german on 04.10.14.
 */
public class ExpressionParser {
    private static final String allowableSymbols = "0123456789.()+-*/";
    private static final String allowableOperations = "+-*/";
    private static String s;
    private static int it;
    private static int bracketsBalance = 0;

    private static void deleteSpaces() {
        while (it < s.length() && Character.isWhitespace(s.charAt(it))) {
            it++;
        }
    }

    private static double getNumber(boolean isNegative) throws CalculationException {
        String number = "";
        if (isNegative) {
            number = "-";
        }
        while (it < s.length()) {
            char c = s.charAt(it);
            if (c >= '0' && c <= '9' || c == '.' || c == 'E') {
                number += c;
            } else if (!Character.isWhitespace(c)) {
                break;
            }
            it++;
        }
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new CalculationException(e.getMessage());
        }
    }

    private static Expression getOperand() throws CalculationException {
        if (it == s.length()) {
            throw new CalculationException("Parsing error: expected some operand");
        }

        char curSymbol = s.charAt(it);
        if (curSymbol == '-' || curSymbol == '+') {
            it++;
            deleteSpaces();
            boolean isNegative = (curSymbol == '-');
            if (it == s.length()) {
                String unaryType = (isNegative ? "minus" : "plus");
                throw new CalculationException("Parsing error: Expression after " + unaryType +
                        " minus at position" + it + "not found");
            }
            char c = s.charAt(it);
            if (c >= '0' && c <= '9' || c == '.') {
                return new Const(getNumber(isNegative));
            } else {
                if (isNegative) {
                    return new UnaryMinus(getBracket());
                } else {
                    return new UnaryPlus(getBracket());
                }
            }
        } else if (curSymbol >= '0' && curSymbol <= '9' || curSymbol == '.') {
            return new Const(getNumber(false));
        } else {
            throw new CalculationException("Parsing error: Unknown symbol " + curSymbol + " of operand");
        }
    }

    private static Expression getBracket() throws CalculationException {
        deleteSpaces();
        Expression res;
        if (it < s.length() && s.charAt(it) == '(') {
            bracketsBalance++;
            it++;
            res = getExpression();
            deleteSpaces();
            if (it == s.length() || s.charAt(it) != ')') {
                throw new CalculationException("Parsing error: ) at position " + it + " expected");
            } else {
                it++;
                bracketsBalance--;
            }
        } else {
            res = getOperand();
        }
        return res;
    }

    private static Expression getFactor() throws CalculationException {
        Expression res = getBracket();
        while (it < s.length()) {
            switch (s.charAt(it)) {
                case '*':
                    it++;
                    res = new Multiply(res, getBracket());
                    break;
                case '/':
                    it++;
                    res = new Divide(res, getBracket());
                    break;
                default:
                    char c = s.charAt(it);
                    if (allowableOperations.indexOf(c) != -1 || (c == ')' && bracketsBalance > 0)) {
                        return res;
                    }
                    String error = "Parsing error: ";
                    if (c == ')' && bracketsBalance <= 0) {
                        error += "( not found";
                    } else {
                        error += "unknown operation " + c;
                    }
                    throw new CalculationException(error);
            }
        }
        return res;
    }

    private  static Expression getExpression() throws CalculationException {
        Expression res = getFactor();
        while (it < s.length()) {
            switch (s.charAt(it)) {
                case '+':
                    it++;
                    res = new Add(res, getFactor());
                    break;
                case '-':
                    it++;
                    res = new Subtract(res, getFactor());
                    break;
                default:
                    return res;
            }
        }
        return res;
    }

    public static Expression parse(String input) throws CalculationException {
        s = input;
        it = 0;
        Expression res = getExpression();
        return res;
    }
}
