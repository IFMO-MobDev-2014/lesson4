package ru.ifmo.md.lesson4;

public abstract class AbstractUnaryOperation implements Expression {

	protected final Expression exp;

	public AbstractUnaryOperation(Expression expression) {
		this.exp = expression;
	}

	public abstract double operate(double value) throws CalculationException;

	@Override
    public double calculate() throws CalculationException {
        return operate(exp.calculate());
    }

}

