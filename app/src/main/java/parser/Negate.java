package parser;

import ru.ifmo.md.lesson4.CalculationException;

public class Negate implements Expression3 {
    private Expression3 arg;
    public Negate(Expression3 arg) {
        this.arg = arg;
    }
    public double evaluate(double x, double y, double z) throws DivisionByZeroException, CalculationException{
        return -arg.evaluate(x, y, z);
    }
}