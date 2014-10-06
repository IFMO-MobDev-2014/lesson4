package ru.ifmo.md.lesson4;

import parser.DivisionByZeroException;

public interface CalculationEngine {

    public double calculate(String expression) throws CalculationException;

}
