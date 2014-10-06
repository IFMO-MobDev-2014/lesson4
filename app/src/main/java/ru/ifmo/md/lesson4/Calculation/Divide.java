package ru.ifmo.md.lesson4.Calculation;

public class Divide extends AbstractOperation {

    public Divide(Expression a, Expression b) {
        super(a, b);
    }   
    
    @Override
    protected double operation(double a, double b)throws DBZException{
        double eps = 0.000000001;
        if(b < eps) throw new DBZException();
        return a / b;
    }
}
