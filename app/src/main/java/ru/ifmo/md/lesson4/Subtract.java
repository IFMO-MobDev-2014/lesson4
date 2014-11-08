package ru.ifmo.md.lesson4;

/**
 * Created by Юлия on 05.10.2014.
 */
class Subtract extends BinaryOperations{

    Subtract(Expression3 o, Expression3 t) {
        super(o, t);
    }

    double operation(double x, double y) {
        return x - y;
    }
}

