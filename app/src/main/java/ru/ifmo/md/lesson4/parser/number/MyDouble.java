package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.DivisionByZeroException;
import ru.ifmo.md.lesson4.parser.exceptions.LogException;
import ru.ifmo.md.lesson4.parser.exceptions.OverflowException;
import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 21.04.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class MyDouble extends MyNumber<MyDouble> {
    private final double eps = 1e-9;

    final public double value;

    public MyDouble() {
        value = 0;
    }

    public MyDouble(double value) {
        this.value = value;
    }

    @Override
    public MyDouble add(MyDouble x) throws OverflowException {
        double a = value, b = x.value;
        return new MyDouble(a + b);
    }

    @Override
    public MyDouble subtract(MyDouble x) throws OverflowException {
        double a = value, b = x.value;
        return new MyDouble(a - b);
    }

    @Override
    public MyDouble multiply(MyDouble x) throws OverflowException {
        double a = value, b = x.value;
        return new MyDouble(a * b);
    }

    @Override
    public MyDouble divide(MyDouble x) throws OverflowException, DivisionByZeroException {
        double a = value, b = x.value;
        if (Math.abs(b) < 1e-9) {
            throw new DivisionByZeroException("Division by zero");
        }
        return new MyDouble(a / b);
    }

    @Override
    public MyDouble unaryAbs() throws OverflowException {
        return new MyDouble(Math.abs(value));
    }

    @Override
    public MyDouble unaryNot() {
        return new MyDouble(value); // will never happen
    }

    @Override
    public MyDouble unaryMinus() throws OverflowException {
        return new MyDouble(-value);
    }

    @Override
    public int compareTo(MyDouble x) {
        return Double.compare(value, x.value);
    }

    @Override
    public MyDouble unaryLb() throws LogException {
        double x = value;
        if (x < eps) {
            throw new LogException("log error");
        }
        x = Math.log(x);
        return new MyDouble(x);
    }

    @Override
    public MyDouble pow(MyDouble x) throws OverflowException {
        double a = value, b = x.value;
        return new MyDouble(Math.pow(a, b));
    }

    @Override
    public MyDouble parse(String s) throws ParseException {
        double result;
        try {
            result = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new ParseException();
        }
        return new MyDouble(result);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public Types.type getType() {
        return Types.type.MyDouble;
    }
}