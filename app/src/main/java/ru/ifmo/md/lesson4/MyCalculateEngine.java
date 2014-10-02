package ru.ifmo.md.lesson4;

/**
 * Created by User on 02.10.2014.
 */
public class MyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        return ExpressionParser.parse(expression).evaluate();
    }
}
