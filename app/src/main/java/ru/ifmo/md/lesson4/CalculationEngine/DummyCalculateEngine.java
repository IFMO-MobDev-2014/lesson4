package ru.ifmo.md.lesson4.CalculationEngine;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;
import ru.ifmo.md.lesson4.Parser.Parser;

public class DummyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        return Parser.parse(expression).evaluate();
    }
}
