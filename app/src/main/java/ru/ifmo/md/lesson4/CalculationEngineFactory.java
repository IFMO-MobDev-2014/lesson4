package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.Calculation.MyCalculationEngine;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
        return new MyCalculationEngine();
    }
}
