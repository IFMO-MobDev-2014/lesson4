package ru.ifmo.md.lesson4;

public class Const implements Expression3 {
    private final double constant;

    public Const(double constant) {
        this.constant = constant;
    }

    public double evaluate(double x, double y, double z) {
        return constant;
    }
}
