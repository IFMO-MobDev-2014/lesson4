package ru.ifmo.md.lesson4.expression;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public class Const implements Expression {
    private final double value;

    public Const(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}
