package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.Calculation.Add;
import ru.ifmo.md.lesson4.Calculation.Const;
import ru.ifmo.md.lesson4.Calculation.DBZException;
import ru.ifmo.md.lesson4.Calculation.Divide;
import ru.ifmo.md.lesson4.Calculation.Expression;
import ru.ifmo.md.lesson4.Calculation.Multiply;
import ru.ifmo.md.lesson4.Calculation.Negative;
import ru.ifmo.md.lesson4.Calculation.Substract;

public class DummyCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException, DBZException {
        return 10;
    }
}
