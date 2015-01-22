package ru.ifmo.md.lesson4;

public class CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
<<<<<<< HEAD
        return new MyCalculatorEngine();
=======
        return new DummyCalculateEngine();
>>>>>>> aa1abad81687c79e37ccbd0aec782e36f808139a
    }
}
