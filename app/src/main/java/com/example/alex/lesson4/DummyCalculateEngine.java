package com.example.alex.lesson4;

import com.example.alex.lesson4.CalculationEngine;

public class DummyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        return 10;
    }
}
