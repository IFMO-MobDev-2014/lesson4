package ru.ifmo.md.lesson4;

/**
 * Created by Юлия on 05.10.2014.
 */
public class UnaryMinus implements Expression3{
    Expression3 o;

    UnaryMinus(Expression3 l) {
        o = l;
    }

    public double evaluate() {
        return -o.evaluate();
    }
}
