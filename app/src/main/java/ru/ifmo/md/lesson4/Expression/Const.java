package ru.ifmo.md.lesson4.Expression;

public class Const implements Expression3 {
    private double value;

    public Const(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public double evaluate(double x, double y, double z) {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Expression3 x) {
        return x instanceof Const && ((Const) x).value == value;
    }

    public boolean equals(double x) {
        return x == value;
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
