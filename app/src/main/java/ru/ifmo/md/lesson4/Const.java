package ru.ifmo.md.lesson4;

/**
 * Created by Юлия on 05.10.2014.
 */
public class Const implements Expression3{
    double o;

    Const(double l) {
        o = l;
    }

    public double evaluate() {
        return o;
    }
}

