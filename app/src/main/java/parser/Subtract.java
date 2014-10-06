package parser;

public class Subtract extends BinaryExpression {
    public Subtract(Expression3 left, Expression3 right) {
        super(left, right);
    }
    public double calculate(double a, double b) {
        return a - b;
    }
}