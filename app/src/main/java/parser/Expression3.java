package parser;

import ru.ifmo.md.lesson4.CalculationException;

public interface Expression3 {
    abstract double evaluate(double x, double y, double z) throws CalculationException;
}