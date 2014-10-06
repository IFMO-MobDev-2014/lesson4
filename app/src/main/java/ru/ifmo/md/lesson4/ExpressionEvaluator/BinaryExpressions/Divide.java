package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public class Divide extends BinaryOperation {
    public Divide(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double evaluate() throws ArithmeticException{
        return leftOperand.evaluate() / rightOperand.evaluate();
    }
}
