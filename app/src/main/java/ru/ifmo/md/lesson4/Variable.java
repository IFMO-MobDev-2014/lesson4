package ru.ifmo.md.lesson4;

public class Variable implements Expression3 {
    private char v;

    public Variable(char v) {
        this.v = v;
    }

    public double evaluate(double x, double y, double z) {
        switch (v) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            default:
                return 0;
        }
    }
}
