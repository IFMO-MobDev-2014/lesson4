package ru.ifmo.md.lesson4;

public class Add extends AbstractBinaryOperation implements Expression{

	public Add(Expression e1, Expression e2) {
		super(e1,e2);
	}

	@Override
    public double operate(double leftValue, double rightValue) throws CalculationException {
		return leftValue + rightValue;
	}

}
