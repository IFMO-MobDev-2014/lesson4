package ru.ifmo.md.lesson4;

public class Const implements Expression {
    private final double val;
    public double evaluate() {
        return val;
    }
    public Const(double val) {
        this.val = val;
    }
}
