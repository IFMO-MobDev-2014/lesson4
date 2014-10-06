package ru.ifmo.md.lesson4.engine;

import android.util.Log;

import ru.ifmo.md.lesson4.exception.CalculationException;

public class DummyCalculateEngine implements CalculationEngine {

    private char[] s;
    private int pos;

    @Override
    public double calculate(String expression) throws CalculationException {
        s = expression.toCharArray();
        pos = 0;

        double res = calculateSum();
        if (pos > s.length) {
            throw new CalculationException();
        }

        return res;
    }

    private double calculateSum() throws CalculationException {
        double res = calculateMultiplication();
        while (pos < s.length && (s[pos] == '+' || s[pos] == '-')) {
            char c = s[pos];
            pos++;
            double rhs = calculateMultiplication();
            if (c == '+') {
                res += rhs;
            } else {
                res += rhs;
            }
        }

        return res;
    }

    private double calculateMultiplication() throws CalculationException {
        double res = calculateTerm();
        while (pos < s.length && (s[pos] == '*' || s[pos] == '/')) {
            char c = s[pos];
            pos++;
            double rhs = calculateTerm();
            if (c == '*') {
                res *= rhs;
            } else {
                res /= rhs;
            }
        }

        return res;
    }

    private double calculateTerm() throws CalculationException {
        if (pos >= s.length) {
            throw new CalculationException();
        }

        if (s[pos] == '-') {
            pos++;
            return calculateTerm();
        }

        if (s[pos] == '(') {
            pos++;
            double res = calculateSum();
            if (pos >= s.length) {
                throw new CalculationException();
            }
            if (s[pos] != ')') {
                throw new CalculationException();
            }
            pos++;
            return res;
        }

        if (!Character.isDigit(s[pos]) && s[pos] != '.') {
            throw new CalculationException();
        }

        StringBuilder builder = new StringBuilder();
        while (pos < s.length && (Character.isDigit(s[pos]) || s[pos] == '.')) {
            builder.append(s[pos]);
            pos++;
        }

        double res;
        try {
            Log.d("APP", builder.toString());
            res = Double.parseDouble(builder.toString());
        } catch (NumberFormatException e) {
            throw new CalculationException();
        }

        return res;
    }
}
