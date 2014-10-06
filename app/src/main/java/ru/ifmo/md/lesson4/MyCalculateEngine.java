package ru.ifmo.md.lesson4;

/**
 * Created by vlad107 on 06.10.14
 */
public class MyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        calcIt res = new calcIt();
        return Double.valueOf(res.Evaluate(expression));
    }
}
