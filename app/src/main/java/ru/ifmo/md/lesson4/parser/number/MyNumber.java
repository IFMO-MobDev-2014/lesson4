package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.DivisionByZeroException;
import ru.ifmo.md.lesson4.parser.exceptions.LogException;
import ru.ifmo.md.lesson4.parser.exceptions.OverflowException;
import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 21.04.14
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public abstract class MyNumber<T> {
    public abstract T add(T x) throws OverflowException;
    public abstract T subtract(T x) throws OverflowException;
    public abstract T multiply(T x) throws OverflowException;
    public abstract T divide(T x) throws OverflowException, DivisionByZeroException;
    public abstract T unaryAbs() throws OverflowException;
    public abstract T unaryNot();
    public abstract T unaryMinus() throws OverflowException;
    public abstract T unaryLb() throws LogException;
    public abstract T pow(T x) throws OverflowException;
    public abstract int compareTo(T a);
    public abstract String toString();
    public abstract T parse(String s) throws ParseException;
    public abstract Types.type getType();
}