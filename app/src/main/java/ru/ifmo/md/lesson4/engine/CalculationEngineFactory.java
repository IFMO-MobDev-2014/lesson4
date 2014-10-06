package ru.ifmo.md.lesson4.engine;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
        return new DummyCalculateEngine();
    }
}
