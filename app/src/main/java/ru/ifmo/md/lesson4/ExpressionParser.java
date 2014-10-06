package ru.ifmo.md.lesson4;

public class ExpressionParser implements CalculationEngine {

    static private String current;
    static private Lexer lex;

    static private class Lexer {
        private final String s;
        private int cur;

        Lexer(String s) {
            this.s = s;
            cur = 0;
        }

        String next() {
            if (cur == s.length()) {
                return "";
            }
            if (Character.isDigit(s.charAt(cur))) {
                int j = cur;
                while (j + 1 < s.length() && Character.isDigit(s.charAt(j + 1))) {
                    j++;
                }
                int buff = cur;
                cur = j + 1;
                return s.substring(buff, j + 1);
            }
            cur++;
            return s.substring(cur - 1, cur);
        }
    }

    static private double parseValue() throws CalculationException {
        if (current.charAt(0) >= '0' && current.charAt(0) <= '9') {
            return Double.parseDouble(current);
        } else if (current.equals("(")) {
            return parseExpr();
        } else {
            throw new CalculationException();
        }
    }

    static private double parseMultiplier() throws CalculationException {
        current = lex.next();
        if (current.equals("")) {
            throw new CalculationException();
        }
        if (current.equals("-")) {
            return -parseMultiplier();
        }
        return parseValue();
    }

    static private double parseSum() throws CalculationException {
        double left = parseMultiplier();
        while (true) {
            current = lex.next();
            if (!current.equals("*") && !current.equals("/") && !current.equals("%")) {
                return left;
            }
            if (current.equals("*")) {
                left = left * parseMultiplier();
            }
            if (current.equals("/")) {
                left = left / parseMultiplier();
            }
        }
    }

    static private double parseExpr() throws CalculationException {
        double left = parseSum();
        while (true) {
            if (current.equals(")") || current.equals("")) {
                return left;
            }
            switch (current.charAt(0)) {
                case '+': {
                    left = left + parseSum();
                    break;
                }
                case '-': {
                    left = left - parseSum();
                    break;
                }
            }
        }
    }
    @Override
    public double calculate(String s) throws CalculationException {
        lex = new Lexer(s);
        return parseExpr();
    }
}