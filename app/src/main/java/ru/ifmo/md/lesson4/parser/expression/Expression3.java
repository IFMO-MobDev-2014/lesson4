package ru.ifmo.md.lesson4.parser.expression;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

public interface Expression3<T extends MyNumber<T>> {
    public T evaluate(T x, T y, T z) throws MyException;
}
