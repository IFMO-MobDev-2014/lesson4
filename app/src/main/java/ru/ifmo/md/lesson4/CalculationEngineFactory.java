package ru.ifmo.md.lesson4;

import ru.ifmo.ctddev.soloveva.lesson4.Calculator;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
        return new Calculator();
    }
}
