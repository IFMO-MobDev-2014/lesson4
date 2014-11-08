package ru.ifmo.md.lesson4;

import parser.DivisionByZeroException;
import parser.ExpressionParser;


public class MyCalculationEngine implements CalculationEngine {

    public double calculate(String expression) throws CalculationException {
            return ExpressionParser.parse(expression).evaluate(0, 0, 0);
    }
}
