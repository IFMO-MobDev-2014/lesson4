package ru.ifmo.md.lesson4;


/*
 * Coś się kończy, coś się zaczyna
 */
public class Add extends Action{

    public Add(Expression a, Expression b){
        super(a, b);
    }

    public double complete(double a1, double b1) {
            return a1 + b1;
    }
}
