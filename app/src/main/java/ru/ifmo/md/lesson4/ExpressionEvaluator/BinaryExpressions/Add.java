package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public class Add extends BinaryOperation {
    public Add(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double evaluate() throws ArithmeticException {
        return leftOperand.evaluate() + rightOperand.evaluate();
    }
}
