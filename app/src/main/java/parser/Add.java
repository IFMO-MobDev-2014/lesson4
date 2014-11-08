package parser;

public class Add extends BinaryExpression {
    public Add(Expression3 left, Expression3 right) {
        super(left, right);
    }
    public double calculate(double a, double b) {

            return a + b;
        }

}