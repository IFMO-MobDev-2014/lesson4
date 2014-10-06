package ru.ifmo.md.lesson4.Calculation;

public abstract class AbstractOperation implements Expression {

    private Expression a, b;

    public AbstractOperation(Expression a, Expression b) {
        this.a = a;
        this.b = b; 
    }

    @Override
    public double evaluate() throws DBZException{
        return operation(a.evaluate(), b.evaluate());
    }

    protected abstract double operation(double a, double b) throws DBZException;
}
