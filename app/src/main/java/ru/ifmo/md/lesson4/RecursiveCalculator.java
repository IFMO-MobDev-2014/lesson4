package ru.ifmo.md.lesson4;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ru.ifmo.md.lesson4.expression.*;

public class RecursiveCalculator implements CalculationEngine {
    private String s;
    private int next;
    private final char terminalChar = '$';

    private char getChar() {
        if (next < 0 || next >= s.length()) {
            next++;
            return terminalChar;
        }
        return s.charAt(next++);
    }

    private void returnChar() {
        next--;
    }

    private void skipWhitespaces() throws CalculationException {
        char c = getChar();
        while (Character.isWhitespace(c)) {
            c = getChar();
        }
        returnChar();
    }

    private boolean isDigit(char c) {
        if ((c >= '0' && c <= '9') || c == '.') {
            return true;
        }
        return false;
    }

    private Constant getConstant(char c) throws CalculationException {
        StringBuilder sb = new StringBuilder();
        while (isDigit(c)) {
            sb.append(c);
            c = getChar();
        }
        returnChar();
        try {
            return new Constant(Double.parseDouble(sb.toString()));
        } catch (NumberFormatException e) {
            throw new CalculationException();
        }
    }

    private Expression parseFactor() throws CalculationException {
        skipWhitespaces();
        char c = getChar();

        if (isDigit(c)) {
            return getConstant(c);
        }

        Expression result;
        switch (c) {
            case '-':
                return new UnaryMinus(parseFactor());
            case '(':
                result = parseFormula();
                skipWhitespaces();
                if (getChar() == ')') {
                    return result;
                }
                throw new CalculationException();
            default:
                throw new CalculationException();
        }
    }

    private Expression parsePow(Expression left) throws CalculationException {
        skipWhitespaces();
        char c = getChar();

        switch (c) {
            case '^':
                return new Power(left, parsePow(parseFactor()));
            default:
                returnChar();
                return left;
        }
    }

    private Expression parseMultiply(Expression left) throws CalculationException {
        skipWhitespaces();
        char c = getChar();

        switch (c) {
            case '*':
                return parseMultiply(new Multiply(left, parsePow(parseFactor())));
            case '/':
                return parseMultiply(new Divide(left, parsePow(parseFactor())));
            default:
                returnChar();
                return left;
        }
    }

    private Expression parsePlus(Expression left) throws CalculationException {
        skipWhitespaces();
        char c = getChar();

        switch (c) {
            case '+':
                return parsePlus(new Plus(left, parseMultiply(parsePow(parseFactor()))));
            case '-':
                return parsePlus(new Subtract(left, parseMultiply(parsePow(parseFactor()))));
            default:
                returnChar();
                return left;
        }
    }

    private Expression parseFormula() throws CalculationException {
        return parsePlus(parseMultiply(parsePow(parseFactor())));
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        s = expression;
        next = 0;
        Expression parsedExpression = parseFormula();
        if (next < s.length()) {
            throw new CalculationException();
        }
        return parsedExpression.calculate();
    }

    public static void main(String[] args) {
        final int tests = 10;
        Random rnd = new Random();
        for (int test = 0; test < tests; test++) {
            final int numbersCount = 9;
            ArrayList<Integer> numbers = new ArrayList<>();
            ArrayList<Integer> revNumbers = new ArrayList<>();
            for (int i = 0; i < numbersCount; i++) {
                int t = rnd.nextInt(10) + 1;
                numbers.add(t);
                revNumbers.add(t);
            }
            Collections.shuffle(revNumbers);
            StringBuilder expressionString = new StringBuilder();
            expressionString.append(numbers.get(0)).append("/").append(revNumbers.get(0));
            for (int i = 1; i < numbersCount; i++) {
                expressionString.append("*").append(numbers.get(i)).append("/").append(revNumbers.get(i));
            }
            try {
                System.out.println(CalculationEngineFactory.defaultEngine().calculate(expressionString.toString()));
            } catch (CalculationException e) {
                e.printStackTrace();
            }
        }
    }

}
