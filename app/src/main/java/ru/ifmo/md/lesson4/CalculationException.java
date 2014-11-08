package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {
    CalculationException(){
        super("Invalid expression.");
    }
}
