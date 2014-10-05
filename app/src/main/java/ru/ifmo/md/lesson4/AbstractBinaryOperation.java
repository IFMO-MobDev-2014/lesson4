package ru.ifmo.md.lesson4;

public abstract class AbstractBinaryOperation implements Expression {

	protected final Expression left, right;

	public AbstractBinaryOperation(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	protected abstract double operate(double leftValue, double rightValue) throws CalculationException;

	@Override
    public double calculate() throws CalculationException {
		return operate(left.calculate(), right.calculate());
	}



}
