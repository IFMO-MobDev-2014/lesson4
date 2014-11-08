package parser;

public class Multiply extends BinaryExpression {
    public Multiply(Expression3 left, Expression3 right) {
        super(left, right);
    }
    public double calculate(double a, double b) {
        return a * b;
    }
}