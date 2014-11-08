package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {
    @Override
    public String toString() {
        return "Error while calculation";
    }
}
