package ru.ifmo.md.lesson4.MyCalcEngine;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * @author sugak andrey
 */
public interface Expression {
    abstract double eval() throws CalculationException;
}
