package ru.ifmo.md.lesson4.parser.expression;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.exceptions.ParseException;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

public class Variable<T extends MyNumber<T>> implements Expression3<T> {
    private final String name;

    public Variable(String s) {
        name = s;
    }

    public T evaluate(T x, T y, T z) throws MyException {
        switch (name) {
            case "x": return x;
            case "y": return y;
            case "z": return z;
        }
        throw new ParseException("wrong variable name");
    }

}
