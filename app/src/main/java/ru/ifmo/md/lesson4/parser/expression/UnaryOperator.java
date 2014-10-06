package ru.ifmo.md.lesson4.parser.expression;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

public abstract class UnaryOperator<T extends MyNumber<T>> implements Expression3<T> {
    protected final Expression3<T> e;
    protected final MyFunction<T> f;

    public UnaryOperator() {
        e = null;
        f = null;
    }

    public UnaryOperator(Expression3<T> e, MyFunction<T> f) {
        this.e = e;
        this.f = f;
    }

    public T evaluate(T x, T y, T z) throws MyException {
       T a = e.evaluate(x, y, z);
       return f.evalImpl(a);
    }
}
