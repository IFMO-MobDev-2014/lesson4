package ru.ifmo.md.lesson4;

public class BinMin extends Base  {

    BinMin (Expression3 a) {
        super(a, new Const(0));
    }

    public int perform(int x, int y) {
        return ~x;
    }
}

