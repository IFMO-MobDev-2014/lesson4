package ru.ifmo.md.lesson4;

public class Variable implements Expression3 {

    String x;

    Variable (String x) {
        this.x = x;
    }

    public int evaluate (int x, int y, int z) {
        if (this.x.equals("x")) return x;
        if (this.x.equals("y")) return y;
        return z;
    }
}
