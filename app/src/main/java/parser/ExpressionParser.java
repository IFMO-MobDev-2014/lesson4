package parser;

import ru.ifmo.md.lesson4.CalculationException;

public class ExpressionParser {

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
            if (Character.isDigit(s.charAt(cur)) || (s.charAt(cur) == '.')) {
                int j = cur;
                while (j + 1 < s.length() && (Character.isDigit(s.charAt(j + 1)) || (s.charAt(j+1) == '.'))) {
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

    static private Expression3 parseValue() throws CalculationException {
        String s = current;
        if (s.length() == 0) {
            throw new CalculationException();
        }
        if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
            try {
                return new Const(Double.parseDouble(s));
            } catch (Exception e) {
                throw new CalculationException();
            }
        } else if (s.equals("(")) {
            return parseExpr();
        } else {
            throw new CalculationException();
        }
    }

    static private Expression3 parseMultiplier() throws CalculationException {
        current = lex.next();
        if (current.equals("")) {
            throw new CalculationException();
        }
        if (current.equals("-")) {
            return new Negate(parseMultiplier());
        }
        return parseValue();
    }

    static private Expression3 parseSum() throws CalculationException {
        Expression3 left = parseMultiplier();
        while (true) {
            current = lex.next();
            if (!current.equals("*") && !current.equals("/") && !current.equals("%")) {
                return left;
            }
            switch (current.charAt(0)) {
                case '*': {
                    left = new Multiply(left, parseMultiplier());
                    break;
                }
                case '/': {
                    left = new Divide(left, parseMultiplier());

                }
            }
        }
    }

    static private Expression3 parseExpr() throws CalculationException {
        Expression3 left = parseSum();
        while (true) {
            if (current.equals(")") || current.equals("")) {
                return left;
            }
            switch (current.charAt(0)) {
                case '+': {
                    left = new Add(left, parseSum());
                    break;
                }
                case '-': {
                    left = new Subtract(left, parseSum());
                    break;
                }
            }
        }
    }

    static public Expression3 parse(String s) throws CalculationException {
        int stack = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '!' && i > 0 && i < s.length() - 1) {
                if (Character.isDigit(s.charAt(i - 1)) && Character.isDigit(s.charAt(i + 1)) ||
                        Character.isLetter(s.charAt(i - 1)) && Character.isLetter(s.charAt(i + 1)) ||
                        ((s.charAt(i - 1)) == '+' || s.charAt(i - 1) == '*' ||s.charAt(i - 1) == '/' &&
                        s.charAt(i + 1) == '+' || s.charAt(i + 1) == '*' ||s.charAt(i + 1) == '/'))
                    throw new CalculationException();

            }
            if (s.charAt(i) == '-' && i > 0 && i < s.length() - 1 && ((s.charAt(i - 1)) == '+' || s.charAt(i - 1) == '*' ||s.charAt(i - 1) == '/' &&
                    s.charAt(i + 1) == '+' || s.charAt(i + 1) == '*' ||s.charAt(i + 1) == '/')) {
                throw new CalculationException();
            }
            if (s.charAt(i) == '(') {
                stack++;
            }
            if (s.charAt(i) == ')') {
                stack--;
            }
            if (stack < 0) {
                throw new CalculationException();
            }
        }
        if (stack != 0) {
            throw new CalculationException();
        }
        lex = new Lexer(s);
        return parseExpr();
    }
}