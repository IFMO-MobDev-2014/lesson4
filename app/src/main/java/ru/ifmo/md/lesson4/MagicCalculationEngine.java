package ru.ifmo.md.lesson4;

import parser.DivisionByZeroException;
import parser.ExpressionParser;

/**
 * Created by default on 06.10.14.
 */

public class MagicCalculationEngine implements CalculationEngine {

    public double calculate(String expression) throws CalculationException {
        try {
            return ExpressionParser.parse(expression).evaluate(0, 0, 0);
        } catch (DivisionByZeroException e) {
            return Double.NaN;
        }
    }
}
