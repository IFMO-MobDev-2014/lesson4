package ru.ifmo.md.lesson4.Parser;

/**
 * Created by Женя on 03.10.2014.
 */
public class Const implements Expression {
    private double value;

    public Const(double value) {
        this.value = value;
    }
    public double evaluate() {
        return value;
    }
}
