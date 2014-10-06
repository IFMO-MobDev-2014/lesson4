package ru.ifmo.md.lesson4.parser.expression;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

public class UnaryLb<T extends MyNumber<T>> extends UnaryOperator<T> {
    public UnaryLb() {
        super();
    }

    public UnaryLb(Expression3<T> e) {
        super(e, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return a.unaryLb();
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return null;
            }
        });
    }

}
