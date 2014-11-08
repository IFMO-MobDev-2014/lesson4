package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.engine.DummyCalculateEngine;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
        return new DummyCalculateEngine();
    }
}
