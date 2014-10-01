package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.Expression.Parser.ParsingException;

public interface CalculationEngine {

    public double calculate(String expression) throws ParsingException, CalculationException;

}
