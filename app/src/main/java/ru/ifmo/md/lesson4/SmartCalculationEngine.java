package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.parser.ExpressionParser;

/**
 * Created by german on 05.10.14.
 */
public class SmartCalculationEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        return ExpressionParser.parse(expression).evaluate();
    }
}
