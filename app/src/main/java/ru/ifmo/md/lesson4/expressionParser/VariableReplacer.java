package ru.ifmo.md.lesson4.expressionParser;

public abstract class VariableReplacer {
    VariableReplacer(String name) {
        this.name = name;
    }
    public final String name;
    public abstract Expression3 replace(Object obj);
}