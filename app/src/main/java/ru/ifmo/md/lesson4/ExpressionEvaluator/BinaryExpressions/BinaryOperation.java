package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public abstract class BinaryOperation implements Expression{
	protected final Expression leftOperand;
	protected final Expression rightOperand;

	public BinaryOperation(Expression leftOperand, Expression rightOperand){
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	public abstract double evaluate() throws ArithmeticException;
}
