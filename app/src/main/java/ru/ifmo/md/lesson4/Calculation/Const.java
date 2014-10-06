package ru.ifmo.md.lesson4.Calculation;

public class Const implements Expression{

    private double value;

    public Const(double v) {
        value = v;
    }

    @Override
    public double evaluate(){
        return value;
    }
}
