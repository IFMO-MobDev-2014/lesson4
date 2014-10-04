package ru.ifmo.md.lesson4.logic;

/**
 * Created by Сергей on 15.03.14.
 */
public class Const implements Expression {
    private double value;

    public Const(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "new Const(" + value + ")";
    }

    @Override
    public double evaluate() {
        return value;
    }
}
