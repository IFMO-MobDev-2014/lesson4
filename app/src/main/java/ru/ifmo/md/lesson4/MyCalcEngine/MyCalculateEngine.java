package ru.ifmo.md.lesson4.MyCalcEngine;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by sugakandrey on 19.10.14.
 */
public class MyCalculateEngine implements CalculationEngine {

    public MyCalculateEngine() {
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        ExpressionParser parser = new ExpressionParser();
        return parser.parse(expression).eval();
    }
}
