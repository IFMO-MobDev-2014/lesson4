package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.parser.Parser;

/**
 * Created by mariashka on 10/5/14.
 */
public class MyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        Parser p = new Parser(expression);
        return p.parse().eval();
    }
}
