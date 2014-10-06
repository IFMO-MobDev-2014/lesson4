package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        ExpressionParser d = new ExpressionParser();
        Expression3 e = d.parse(expression);
        return e.evaluate(0,0,0);
    }
}
