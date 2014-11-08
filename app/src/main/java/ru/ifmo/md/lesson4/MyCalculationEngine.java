package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.parser.ExpressionParser;
import ru.ifmo.md.lesson4.parser.expression.Expression3;
import ru.ifmo.md.lesson4.parser.number.HelperDouble;
import ru.ifmo.md.lesson4.parser.number.MyDouble;

/**
 * Created by izban on 06.10.14.
 */
public class MyCalculationEngine implements CalculationEngine {

    @Override
    public double calculate(String expression) throws CalculationException {
        Expression3<MyDouble> tree = ExpressionParser.parse(expression, new HelperDouble());
        return tree.evaluate(new MyDouble(0), new MyDouble(0), new MyDouble(0)).value;
    }
}
