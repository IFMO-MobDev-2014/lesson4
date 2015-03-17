package com.example.alex.lesson4;

import com.example.alex.lesson4.CalculationEngine;

public class  CalculationEngineFactory {

    private CalculationEngineFactory() {

    }

    public static CalculationEngine defaultEngine() {
        return new com.example.alex.lesson4.NormalCalculateEngine();
    }
}
