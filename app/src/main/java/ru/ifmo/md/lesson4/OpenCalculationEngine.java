package ru.ifmo.md.lesson4;

/**
 * @author volhovm
 *         Created on 10/4/14
 */

public class OpenCalculationEngine implements CalculationEngine {

    @Override
    public double calculate(String expression) throws CalculationException {
        return CalculatorWrapper.get(expression);
    }
}
