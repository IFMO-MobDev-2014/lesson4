package ru.ifmo.md.lesson4;

import android.util.Log;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;
import ru.ifmo.md.lesson4.logic.ExceptionsHandle.ParserException;
import ru.ifmo.md.lesson4.logic.Expression;
import ru.ifmo.md.lesson4.logic.ExpressionParser;

/**
 * Created by sergey on 04.10.14.
 */
public class IntelligentCalculateEngine implements CalculationEngine {

    @Override
    public double calculate(String expression) throws CalculationException {
        Log.d("calculate()", "expression = " + expression);
        ExpressionParser parser = new ExpressionParser();
        Expression inputExpression;
        double result = 0;
        try {
            inputExpression = parser.parse(expression);
            try {
                result = inputExpression.evaluate();
            } catch (EvaluationException e) {
                Log.e("Exception during evaluation", e.getMessage());
                throw new CalculationException();
            }
        } catch (ParserException e) {
            Log.e("Parser exception", e.getMessage());
            throw new CalculationException();
        }
        return result;
    }
}
