package ru.ifmo.md.lesson4;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
        return new OpenCalculationEngine();
    }
    public static CalculationEngine dummyEngine() {
        return new DummyCalculateEngine();
    }
}
