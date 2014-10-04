package ru.ifmo.md.lesson4;

import ru.ifmo.ctddev.katunina.Main.ExpressionParser;
import ru.ifmo.ctddev.katunina.Main.ParsingException;

public class CalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        try {
            return ExpressionParser.parseDoubleExpression(expression).evaluate(0., 0., 0.);
        } catch (ParsingException e) {
            throw new CalculationException();
        }
    }
}
