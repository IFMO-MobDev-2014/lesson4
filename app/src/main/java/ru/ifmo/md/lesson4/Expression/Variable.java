package ru.ifmo.md.lesson4.Expression;

public class Variable implements Expression3 {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(double x, double y, double z) {
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        } else if (name.equals("z")) {
            return z;
        } else {
            throw new IllegalArgumentException("Not supported variable name: " + name);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Expression3 x) {
        return x instanceof Variable && ((Variable) x).name.equals(name);
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
