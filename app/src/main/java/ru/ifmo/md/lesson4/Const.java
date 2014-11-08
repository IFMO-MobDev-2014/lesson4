package ru.ifmo.md.lesson4;

public class Const implements Expression {
	private final double value;

	Const(String number) {
		value = Double.parseDouble(number);
	}

	@Override
    public double calculate() throws CalculationException {
        return value;
	}
}
