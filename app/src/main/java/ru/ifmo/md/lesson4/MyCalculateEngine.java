package ru.ifmo.md.lesson4;

/**
 * Created by Алексей on 05.10.2014.
 */
public class MyCalculateEngine implements CalculationEngine {
    private char[] s;
    private int index;

    @Override
    public double calculate(String expression) throws CalculationException {
        s = (expression + "#").toCharArray();
        index = 0;
        double ans = parseExpression(parseTerm(parseFactor()));
        if (index != s.length - 1) {
            throw new CalculationException("Unexpected symbol at " + index + " position");
        }
        return ans;
    }

    private double parseExpression(double left) throws CalculationException {
        char c = getChar();
        if (c != '-' && c != '+') {
            returnChar();
            return left;
        }
        double right = parseTerm(parseFactor());
        if (c == '+') {
            return parseExpression(left + right);
        } else {
            return parseExpression(left - right);
        }
    }

    private double parseTerm(double left) throws CalculationException {
        char c = getChar();
        if (c != '*' && c != '/') {
            returnChar();
            return left;
        }
        double right = parseFactor();
        if (c == '*') {
            return parseTerm(left * right);
        } else {
            return parseTerm(left / right);
        }
    }

    private double parseFactor() throws CalculationException {
        char c = getChar();
        double minus = 1;
        if (c == '(') {
            double res = parseExpression(parseTerm(parseFactor()));
            if (getChar() != ')') {
                throw new CalculationException("Missed closed bracket");
            }
            return res;
        }
        if (c == '-') {
            minus = -1;
            c = getChar();
        }
        if (!isDigit(c)) {
            if (c == ')') {
                throw new CalculationException("Missed open bracket");
            } else {
                throw new CalculationException("Expected number at " + index + " symbol");
            }
        }
        returnChar();
        return minus * getNumber();
    }

    private char getChar() throws CalculationException {
        char ans = s[index];
        index++;
        return ans;
    }

    private void returnChar() {
        index--;
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9' || c == '.';
    }

    private double getNumber() throws CalculationException {
        StringBuilder numberString = new StringBuilder();
        boolean wasPoint = false;
        while (index < s.length && isDigit(s[index])) {
            if (s[index] == '.') {
                if (wasPoint) {
                    throw new CalculationException("Two points in number");
                } else {
                    wasPoint = true;
                }
            }
            numberString.append(s[index]);
            index++;
        }
        if (numberString.toString().equals(".")) {
            throw new CalculationException("\".\" is not number");
        } else {
            return Double.parseDouble(numberString.toString());
        }
    }

}
