package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.ExpressionEvaluator.ExpressionParser.ExpressionParser;

public class MyCalculationEngine implements CalculationEngine{
    @Override
    public double calculate(String expression) throws CalculationException {
        return ExpressionParser.parse(expression).evaluate();
    }
}
