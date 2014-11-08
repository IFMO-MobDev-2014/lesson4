package ru.ifmo.md.lesson4;

public class UnaryMinus extends AbstractUnaryOperation {


	public UnaryMinus(Expression e1) {
		super(e1);
	}

	@Override
    public double operate(double value) throws CalculationException {
		return -value;
	}

}
