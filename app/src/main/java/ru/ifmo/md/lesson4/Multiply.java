package ru.ifmo.md.lesson4;

public class Multiply extends AbstractBinaryOperation implements Expression{

	public Multiply(Expression e1, Expression e2) {
		super(e1, e2);
	}

	@Override
    public double operate(double leftValue, double rightValue) throws CalculationException {
		return leftValue * rightValue;
	}

}
