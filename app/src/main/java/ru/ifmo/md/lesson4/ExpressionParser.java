package ru.ifmo.md.lesson4;


/**
 * Coś się kończy, coś się zaczyna
 * 31.03.2014
 */
public class ExpressionParser {
    static int stack;
    static private String current;
    static private Parser lex;

    static boolean isPartOfExp(char c) {
        return (Character.isDigit(c) || (c == '.') ||  (c == '+') || (c == '-') || (c == '/') || (c == '*')||
                (c == '(') || (c == ')'));
    }

    static private class Parser {
        private final String s;
        private int start;

        Parser(String s) {
            this.s = s;
            start = 0;
        }

        String next() {
            if (start == s.length()) {
                return "";
            }
            if (Character.isDigit(s.charAt(start)) || (s.charAt(start) == '.')) {
                int j = start;
                while (j + 1 < s.length() && (Character.isDigit(s.charAt(j + 1)) || (s.charAt(j+1) == '.'))) {
                    j++;
                }
                int buff = start;
                start = j + 1;
                return s.substring(buff, j + 1);
            }
            start++;
            return s.substring(start - 1, start);
        }
    }

    static private Expression parseValue() throws CalculationException, NumberFormatException {
            String s = current;
            if (s.length() == 0) {
                throw new CalculationException();
            }
            if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                return new Const(Double.parseDouble(s));
            } else if (s.equals("(")) {
                return parseExpr();
            } else {
                throw new CalculationException();
            }
    }

    static private Expression parseMultiplier() throws CalculationException {
        current = lex.next();
        String s = current;
        if (s.length() == 0) {
            throw new CalculationException();
        }
        if (current.equals("-")) {
            return new Negate(parseMultiplier());
        }
        return parseValue();
    }

    static private Expression parseSum() throws CalculationException {
        Expression left = parseMultiplier();
        while (true) {
            current = lex.next();
            String s = current;
            if (!s.equals("*") && !s.equals("/") ) {
                return left;
            }
            switch (s.charAt(0)) {
                case '*': {
                    left = new Multiply(left, parseMultiplier());
                    break;
                }
                case '/': {
                    left = new Divide(left, parseMultiplier());
                    break;
                }
            }
        }
    }

    static private Expression parseExpr() throws CalculationException {
        Expression left = parseSum();
        boolean checked = false;
        while (true) {
            if (checked) {
                throw new CalculationException();
            }
            String s = current;
            if (s.equals(")") || s.equals("")) {
                checked = false;
                return left;
            }
            else if (s.length() == 0) {
                throw new CalculationException();
            }
            else if(s.charAt(0) == '+' || s.charAt(0) == '-') switch (s.charAt(0)) {
                case '+': {
                    left = new Add(left, parseSum());
                    checked = false;
                    break;
                }
                case '-': {
                    left = new Subtract(left, parseSum());
                    checked = false;
                    break;
                }
            }
            else checked = true;
        }
    }

    static public Expression parse(String s) throws CalculationException {
        stack = 0;
        s = s.replaceAll("\\s+", "!");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isPartOfExp(c)) {
                throw new CalculationException();
            }
            else if (c == '(') {
                stack++;
            }
            else if (c == ')') {
                stack--;
                if (stack < 0) {
                    throw new CalculationException();
                }
            }
            if (i >= 1 && i < s.length() - 1) {
                if (s.charAt(i) == '!' && Character.isDigit(s.charAt(i - 1)) && Character.isDigit(i + 1)) {
                    throw new CalculationException();
                }
            }
        }
        if (stack > 0) {
            throw new CalculationException();
        }
        s = s.replaceAll("!", "");
        lex = new Parser(s);
        return parseExpr();
    }
}
