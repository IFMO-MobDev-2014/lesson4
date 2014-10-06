package ru.ifmo.md.lesson4;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static DummyCalculateEngine defaultEngine() {
        return new DummyCalculateEngine();
    }
}
