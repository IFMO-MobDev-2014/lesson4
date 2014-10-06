package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 05.05.14
 * Time: 9:57
 * To change this template use File | Settings | File Templates.
 */
public class HelperDouble extends Helper<MyDouble> {
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
}
