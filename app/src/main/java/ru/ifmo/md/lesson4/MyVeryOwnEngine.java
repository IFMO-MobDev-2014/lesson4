package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class MyVeryOwnEngine implements CalculationEngine{
    @Override
    public double calculate(String expression) throws CalculationException {
        try {
            BigDecimal ans = (RecursiveDescentParser.parse(expression)).evaluate();
            return ans.doubleValue();
        } catch (Exception e) {
            throw new CalculationException();
        }
    }
}
