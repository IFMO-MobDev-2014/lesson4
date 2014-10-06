package ru.ifmo.ctddev.soloveva.lesson4;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by maria on 05.10.14.
 */
public class Calculator implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        return calculate(expression, 0, expression.length());
    }

    private double calculate(String expression, int from, int to) throws CalculationException {
        if (from > to - 1) {
            throw new CalculationException();
        }
        int plus = -1, minus = -1, mult = -1, div = -1;
        for (int i = from, z = 0; i < to; ++i) {
            char c = expression.charAt(i);
            if (c == '(') ++z;
            else if (c == ')') --z;
            else if (z == 0) {
                switch (c) {
                    case '+': plus = i; break;
                    case '-': minus = i; break;
                    case '*': mult = i; break;
                    case '/': div = i; break;
                }
            } else if (z < 0) {
                throw new CalculationException();
            }
        }
        if (plus != -1) {
            if (plus == from) {
                return +calculate(expression, from + 1, to);
            }
            return calculate(expression, from, plus) + calculate(expression, plus + 1, to);
        }
        if (minus != -1) {
            if (minus == from) {
                return -calculate(expression, from + 1, to);
            }
            return calculate(expression, from, minus) - calculate(expression, minus + 1, to);
        }
        if (mult != -1) {
            return calculate(expression, from, mult) * calculate(expression, mult + 1, to);
        }
        if (div != -1) {
            return calculate(expression, from, div) / calculate(expression, div + 1, to);
        }
        if (expression.charAt(from) == '(' && expression.charAt(to - 1) == ')') {
            return calculate(expression, from + 1, to - 1);
        }
        try {
            return Double.parseDouble(expression.substring(from, to));
        } catch (NumberFormatException e) {
            throw new CalculationException();
        }
    }
}
