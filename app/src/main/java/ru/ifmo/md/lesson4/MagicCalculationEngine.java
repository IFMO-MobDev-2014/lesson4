package ru.ifmo.md.lesson4;

import parser.ExpressionParser;

/**
 * Created by default on 06.10.14.
 */

public class MagicCalculationEngine implements CalculationEngine {

    public double calculate(String expression) throws CalculationException {
        return ExpressionParser.parse(expression).evaluate(0, 0, 0);
    }

}