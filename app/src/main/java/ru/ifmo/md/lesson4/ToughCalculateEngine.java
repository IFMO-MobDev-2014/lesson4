package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.Expression.Exceptions.CalculationException;
import ru.ifmo.md.lesson4.Expression.Parser.ExpressionParser;
import ru.ifmo.md.lesson4.Expression.Parser.ParsingException;

public class ToughCalculateEngine implements CalculationEngine {

    final ExpressionParser parser = new ExpressionParser();

    @Override
    public double calculate(String expression) throws ParsingException, CalculationException {
        return parser.parse(expression).evaluate(0, 0, 0);
    }
}
