package ru.ifmo.md.lesson4;

public class CalculationEngineFactory {

    private static String s;
    private static int cur = 0;

    public static CalculationEngine defaultEngine() {
        return new MyCalculationEngine();
    }
}
