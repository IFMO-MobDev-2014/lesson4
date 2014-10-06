package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public class UnaryMinus implements Expression {
    private final Expression operand;

    public UnaryMinus(Expression operand) {
        this.operand = operand;
    }

    public double evaluate() throws ArithmeticException{
        return -operand.evaluate();
    }
}