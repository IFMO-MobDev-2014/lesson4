package ru.ifmo.md.lesson4;

/**
 * Created by timur on 02.01.15.
 */

public class Const implements Expression {
    private final double value;

    public Const(double value) {
        this.value = value;
    }

    public double evaluate() {
        return value;
    }
}
