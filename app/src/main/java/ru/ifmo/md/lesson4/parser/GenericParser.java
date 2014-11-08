package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.expression.Expression3;
import ru.ifmo.md.lesson4.parser.number.Helper;
import ru.ifmo.md.lesson4.parser.number.HelperBigInteger;
import ru.ifmo.md.lesson4.parser.number.HelperDouble;
import ru.ifmo.md.lesson4.parser.number.HelperInteger;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 21.04.14
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
public class GenericParser {

    <T extends MyNumber<T>> void test(String t, Helper<T> copy) throws MyException {
        Expression3<T> expr = ExpressionParser.parse(t, copy);
        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                try {
                    System.out.print(expr.evaluate(copy.parse(Integer.toString(x)), copy.parse(Integer.toString(y)), copy.parse("0")).toString() + " ");
                } catch (MyException e) {
                    System.out.print("error ");
                } catch (Exception e) {

                }
            }
            System.out.println();
        }
    }

    void run(String[] args) {
        try {
            String type = args[0];
            switch (type) {
                case "-i":
                    test(args[1], new HelperInteger());
                    break;
                case "-d":
                    test(args[1], new HelperDouble());
                    break;
                case "-bi":
                    test(args[1], new HelperBigInteger());
                    break;
            }
        } catch (Exception e) {

        }

    }

    public static void main(String[] args) {
        new GenericParser().run(args);
    }
}
