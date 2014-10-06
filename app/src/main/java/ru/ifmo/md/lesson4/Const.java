package ru.ifmo.md.lesson4;

public class Const implements Expression3  {
    int x;

    Const (int x) {
        this.x = x;
    }

    public int evaluate (int x, int y, int z) {
        return this.x;
    }
}
