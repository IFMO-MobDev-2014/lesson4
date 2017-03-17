package ru.ifmo.md.lesson4;


/*
 * Coś się kończy, coś się zaczyna
 */
public class Divide extends Action{

    public Divide (Expression a, Expression b){
        super(a, b);
    }

    public double complete(double a1, double b1) throws CalculationException {
            if (b1 == 0) {
                throw new CalculationException();
            }
            return a1 / b1;
    }
}
