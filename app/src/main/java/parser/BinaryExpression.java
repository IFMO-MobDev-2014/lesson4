package parser;

import ru.ifmo.md.lesson4.CalculationException;

public abstract class BinaryExpression implements Expression3 {
    private Expression3 left, right;

    public BinaryExpression(Expression3 left, Expression3 right) {
        this.left = left;
        this.right = right;
    }
    public double evaluate(double x, double y, double z) throws CalculationException, DivisionByZeroException{
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    public abstract double calculate(double a, double b) throws CalculationException, DivisionByZeroException;
}
