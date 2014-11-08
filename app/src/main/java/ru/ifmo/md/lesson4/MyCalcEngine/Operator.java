package ru.ifmo.md.lesson4.MyCalcEngine;

/**
 * @author sugak andrey
 */
public enum Operator {
    ADD { double eval(double lhs, double rhs) { return lhs + rhs; }},
    SUBTRACT { double eval(double lhs, double rhs) { return lhs - rhs; }},
    DIVIDE { double eval(double lhs, double rhs) {return lhs / rhs; }},
    MULTIPLY { double eval(double lhs, double rhs) { return lhs * rhs; }};

    abstract double eval(double lhs, double rhs);
}
