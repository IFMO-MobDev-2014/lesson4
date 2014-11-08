package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public class Multiply extends BinaryOperation{
	public Multiply(Expression leftOperand, Expression rightOperand){
		super(leftOperand, rightOperand);
	}

	public double evaluate() throws ArithmeticException {
        return leftOperand.evaluate() * rightOperand.evaluate();
	}
}
