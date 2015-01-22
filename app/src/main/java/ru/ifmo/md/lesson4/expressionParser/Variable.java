package ru.ifmo.md.lesson4.expressionParser;

public class Variable implements Expression3 {
    private char var;
    
    public Variable(String name) {
        var = name.charAt(0);
    }
    
    public double evaluate() {
        return 0;
    }
    
    public String toString() {
        return "" + var;
    }
}