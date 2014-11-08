package ru.ifmo.md.lesson4;

/**
 * Created by siziyman on 05.10.2014.
 */
public class MyCalculationEngine implements CalculationEngine {

    @Override
    public double calculate(String expr) throws CalculationException {
        Expression a = ExpressionParser.parse(expr);
        return a.evaluate();
    }

}
