package ru.ifmo.md.lesson4.Calculation;

public abstract class AbstractUnOperation implements Expression {

    private Expression v;
    
    public AbstractUnOperation(Expression value)
    {
        v = value;
    }
    
    @Override
    public double evaluate() throws DBZException{
        return operation(v.evaluate());
    }
    
    protected abstract double operation(double value);
}
