package ru.ifmo.md.lesson4.expression;

public class Constant implements Expression {
    private double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public double calculate() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Constant constant = (Constant) o;

        return Double.compare(constant.value, value) == 0;
    }
}
