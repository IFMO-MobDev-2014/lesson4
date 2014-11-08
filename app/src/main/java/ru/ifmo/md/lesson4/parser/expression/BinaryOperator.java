package ru.ifmo.md.lesson4.parser.expression;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

public abstract class BinaryOperator<T extends MyNumber<T>> implements Expression3<T> {
    protected final Expression3<T> l, r;
    protected final MyFunction<T> f;

    public BinaryOperator() {
        l = null;
        r = null;
        f = null;
    }

    public BinaryOperator(Expression3<T> l, Expression3<T> r, MyFunction<T> f) {
        this.l = l;
        this.r = r;
        this.f = f;
    }

    public T evaluate(T x, T y, T z) throws MyException {
        T a = l.evaluate(x, y, z);
        T b = r.evaluate(x, y, z);
        return f.evalImpl(a, b);
    }
}
