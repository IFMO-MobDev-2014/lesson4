package ru.ifmo.md.lesson4;

public class Divide extends AbstractBinaryOperation implements Expression{

	public Divide(Expression e1, Expression  e2) {
		super(e1,e2);
	}

	@Override
    public double operate(double leftValue, double rightValue) throws CalculationException {
        if (Double.isNaN(leftValue / rightValue))
        {
            throw new CalculationException();
        }
		return leftValue / rightValue;
	}
}
