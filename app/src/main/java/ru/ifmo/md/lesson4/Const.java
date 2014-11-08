package ru.ifmo.md.lesson4;


/*
 * Coś się kończy, coś się zaczyna
 */
public class Const implements Expression {
    private double val;

    public Const(double val) {
        this.val = val;
    }

    public double evaluate() {
        return val;
    }
}
