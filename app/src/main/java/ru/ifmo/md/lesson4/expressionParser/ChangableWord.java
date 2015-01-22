package ru.ifmo.md.lesson4.expressionParser;

public abstract class ChangableWord {
    public ChangableWord(String name) {
        this.name = name;
    }
    public final String name;
    public abstract Object calc(); 
}