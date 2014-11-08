package parser;

public class Divide extends BinaryExpression {
    public Divide(Expression3 left, Expression3 right) throws DivisionByZeroException{
        super(left, right);
    }
    public double calculate(double a, double b) throws DivisionByZeroException{
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        return a / b;
    }
}