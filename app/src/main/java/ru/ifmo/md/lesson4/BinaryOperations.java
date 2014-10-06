package ru.ifmo.md.lesson4;

/**
 * Created by Юлия on 05.10.2014.
 */
abstract class BinaryOperations implements Expression3 {
    Expression3 one;
    Expression3 two;

    BinaryOperations(Expression3 o, Expression3 t) {
        one = o;
        two = t;
    }

    abstract double operation(double x, double y);

    public double evaluate() {
        return operation(one.evaluate(), two.evaluate());
    }
}

