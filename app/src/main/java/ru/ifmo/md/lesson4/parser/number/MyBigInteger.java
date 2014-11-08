package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.DivisionByZeroException;
import ru.ifmo.md.lesson4.parser.exceptions.LogException;
import ru.ifmo.md.lesson4.parser.exceptions.OverflowException;
import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 21.04.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class MyBigInteger extends MyNumber<MyBigInteger> {
    final public BigInteger value;

    public MyBigInteger() {
        value = BigInteger.ZERO;
    }

    public MyBigInteger(BigInteger value) {
        this.value = value;
    }

    public MyBigInteger(int value) {
        this.value = BigInteger.valueOf(value);
    }

    @Override
    public MyBigInteger add(MyBigInteger x) throws OverflowException {
        BigInteger a = value, b = x.value;
        return new MyBigInteger(a.add(b));
    }

    @Override
    public MyBigInteger subtract(MyBigInteger x) throws OverflowException {
        BigInteger a = value, b = x.value;
        return new MyBigInteger(a.subtract(b));
    }

    @Override
    public MyBigInteger multiply(MyBigInteger x) throws OverflowException {
        BigInteger a = value, b = x.value;
        return new MyBigInteger(a.multiply(b));
    }

    @Override
    public MyBigInteger divide(MyBigInteger x) throws OverflowException, DivisionByZeroException {
        BigInteger a = value, b = x.value;
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("division by zero");
        }
        return new MyBigInteger(a.divide(b));
    }

    @Override
    public MyBigInteger unaryAbs() throws OverflowException {
        return new MyBigInteger(value.abs());
    }

    @Override
    public MyBigInteger unaryNot() {
        return new MyBigInteger(value); // never happen
    }

    @Override
    public MyBigInteger unaryMinus() throws OverflowException {
        return new MyBigInteger(value.negate());
    }

    @Override
    public int compareTo(MyBigInteger x) {
        return value.compareTo(x.value);
    }

    @Override
    public MyBigInteger unaryLb() throws LogException {
        BigInteger x = value;
        if (x.compareTo(BigInteger.ZERO) <= 0) {
            throw new LogException("log error");
        }
        int ans = 0;
        while (x.compareTo(BigInteger.ONE) > 0) {
            x = x.divide(BigInteger.valueOf(2));
            ans++;
        }
        return new MyBigInteger(BigInteger.valueOf(ans));
    }

    BigInteger pow(BigInteger a, BigInteger b) throws OverflowException {
        if (b.equals(BigInteger.ZERO)) return BigInteger.ONE;
        BigInteger result = pow(a, b.divide(BigInteger.valueOf(2)));
        result = result.multiply(result);
        if (!b.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) result = result.multiply(a);
        return result;
    }

    @Override
    public MyBigInteger pow(MyBigInteger x) throws OverflowException {
        BigInteger a = value, b = x.value;
        if (b.compareTo(BigInteger.ZERO) < 0 || a.compareTo(BigInteger.ZERO) == 0 && b.compareTo(BigInteger.ZERO) == 0) {
            throw new OverflowException("pow error");
        }
        return new MyBigInteger(pow(a, b));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public MyBigInteger parse(String s) throws ParseException {
        BigInteger result;
        try {
            result = new BigInteger(s);
        } catch (Exception e) {
            throw new ParseException();
        }
        return new MyBigInteger(result);
    }

    @Override
    public Types.type getType() {
        return Types.type.MyBigInteger;
    }
}