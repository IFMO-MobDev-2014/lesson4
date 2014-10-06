package ru.ifmo.md.lesson4.parser.number;

import ru.ifmo.md.lesson4.parser.exceptions.CalculateException;
import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.exceptions.ParseException;
import ru.ifmo.md.lesson4.parser.expression.BinaryOperator;
import ru.ifmo.md.lesson4.parser.expression.Expression3;
import ru.ifmo.md.lesson4.parser.expression.MyFunction;
import ru.ifmo.md.lesson4.parser.expression.UnaryOperator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 05.05.14
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class Helper<T extends MyNumber<T>> {
    public class Item {
        int prior, operands;
        boolean isLeft;
        MyFunction<T> f;

        Item() {}
        Item(int prior, int operands, boolean isLeft, MyFunction<T> f) {
            this.prior = prior;
            this.operands = operands;
            this.isLeft = isLeft;
            this.f = f;
        }
    }
    Map<String, Item> mp = new HashMap<>();

    {
        mp.put("*", new Item(1, 2, true, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return null;
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return a.multiply(b);
            }
        }));
    }

    {
        mp.put("/", new Item(1, 2, true, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return null;
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return a.divide(b);
            }
        }));
    }

    {
        mp.put("+", new Item(0, 2, true, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return null;
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return a.add(b);
            }
        }));
    }

    {
        mp.put("-", new Item(0, 2, true, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return null;
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return a.subtract(b);
            }
        }));
    }

    {
        mp.put("^", new Item(2, 2, true, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return null;
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return a.pow(b);
            }
        }));
    }

    {
        mp.put("~", new Item(3, 1, false, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return a.unaryNot();
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return null;
            }
        }));
    }

    {
        mp.put("abs", new Item(3, 1, false, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return a.unaryAbs();
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return null;
            }
        }));
    }

    {
        mp.put("-un", new Item(3, 1, false, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                return a.unaryMinus();
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return null;
            }
        }));
    }

    {
        mp.put(")", new Item(-1, 0, false, null));
    }

    {
        mp.put("sin", new Item(3, 1, false, new MyFunction<T>() {
            @Override
            public T evalImpl(T a) throws MyException {
                if (a.getType() == Types.type.MyDouble) {
                    return a.parse(
                            Double.toString(
                                    Math.sin(
                                            Double.parseDouble(
                                                    a.toString()
                                            )
                                    )
                            )
                    );
                } else {
                    throw new CalculateException("invalid operation");
                }
            }

            @Override
            public T evalImpl(T a, T b) throws MyException {
                return null;
            }
        }));
    }

    public abstract T parse(String s) throws ParseException;

    public boolean isVariable(String s) {
        return !mp.containsKey(s);
    }

    public int getPrior(String s) throws ParseException {
        if (!mp.containsKey(s)) {
            throw new ParseException();
        }
        return mp.get(s).prior;
    }

    public int getOperands(String s) throws ParseException {
        if (!mp.containsKey(s)) {
            throw new ParseException();
        }
        return mp.get(s).operands;
    }

    public boolean isLeft(String s) throws ParseException {
        if (!mp.containsKey(s)) {
            throw new ParseException();
        }
        return mp.get(s).isLeft;
    }

    public Expression3<T> applyFunction(String s, Expression3<T> left, Expression3<T> right) throws ParseException {
        if (!mp.containsKey(s)) {
            throw new ParseException("invalid function");
        }
        MyFunction<T> f = mp.get(s).f;
        return new BinaryOperator<T>(left, right, f) {};
    }

    public Expression3<T> applyFunction(String s, final Expression3<T> expr) throws ParseException {
        if (!mp.containsKey(s)) {
            throw new ParseException("invalid function");
        }
        MyFunction<T> f = mp.get(s).f;
        return new UnaryOperator<T>(expr, f) {};
    }
}