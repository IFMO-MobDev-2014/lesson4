package ru.ifmo.md.lesson4.MyCalcEngine;

/**
 * @author sugak andrey
 */
public class Const implements Expression {
    private double value;

    public Const(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public double eval() {
        return value;
    }
}
