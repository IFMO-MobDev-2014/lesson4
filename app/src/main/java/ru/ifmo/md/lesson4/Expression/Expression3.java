package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.CalculationException;

public interface Expression3 {

    public double evaluate(double x, double y, double z) throws CalculationException;

    public String toString();

    public boolean equals(Expression3 x);

    public int getPriority();
}
