package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 05.05.14
 * Time: 9:58
 * To change this template use File | Settings | File Templates.
 */
public class HelperBigInteger extends Helper<MyBigInteger> {
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
}
