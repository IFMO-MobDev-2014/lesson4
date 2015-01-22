package ru.ifmo.md.lesson4.expressionParser;

public class Const implements Expression3 {
    private double value;
    public Const(double value) {
        this.value = value;
    }
    
    public double evaluate() {
        return value;
    }
    
    public String toString() {
        return value + "";
    }
}