package ru.ifmo.md.lesson4;

public class Add extends Base  {

    Add (Expression3 a, Expression3 b) {
        super(a, b);
    }

    public int perform(int x, int y) {
        return x+y;
    }
}
