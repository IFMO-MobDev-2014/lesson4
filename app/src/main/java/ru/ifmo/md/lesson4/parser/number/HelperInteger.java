package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 05.05.14
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */
public class HelperInteger extends Helper<MyInteger> {
    @Override
    public MyInteger parse(String s) throws ParseException {
        int result;
        try {
            result = (int)Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new ParseException();
        }
        return new MyInteger(result);
    }
}
