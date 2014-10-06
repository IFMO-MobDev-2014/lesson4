package ru.ifmo.md.lesson4; 

public abstract class Base implements Expression3 {

    Expression3 a, b;

    Base(Expression3 a, Expression3 b) {
  //      assert a  != null;

        this.a = a;
        this.b = b;
    }

    public int evaluate(int x, int y, int z) {
        return perform(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }

    public static Base recognize(String t) {
        return new Divide(Base.recognize(t), Base.recognize(t));
    }

    public abstract int perform(int x, int y);
}
