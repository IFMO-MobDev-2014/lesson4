package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public class Subtract extends BinaryOperation{
	public Subtract(Expression leftOperand, Expression rightOperand){
		super(leftOperand, rightOperand);
	}

	public double evaluate() throws ArithmeticException{
        return leftOperand.evaluate() - rightOperand.evaluate();
	}
}
