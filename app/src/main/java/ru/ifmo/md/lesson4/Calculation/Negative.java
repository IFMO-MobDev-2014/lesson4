package ru.ifmo.md.lesson4.Calculation;

public class Negative extends AbstractUnOperation {
    
    public Negative(Expression value)
    {
        super(value);
    }
    
    @Override
    public double operation(double v)
    {
        return -v;
    }
}
