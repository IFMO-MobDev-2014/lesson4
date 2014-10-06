package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CalculationEngineTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testCalcEngine() {
        try {
            CalculationEngine engine = CalculationEngineFactory.defaultEngine();
            Assert.assertEquals(12d, engine.calculate("(3+4)-1+6"));
            Assert.assertEquals(0.5d, engine.calculate("0.5*(3/3)"));
            Assert.assertEquals(Math.atan(Math.E + Math.PI) / 2, engine.calculate("atan(e+pi)/2"));
            Assert.assertEquals(Math.PI, engine.calculate("ln(e^pi)"));
            Assert.assertEquals(Math.sqrt(2) * 456.123e1, engine.calculate("456.123e1*sqrt(2)"));
            Assert.assertEquals(1.234, engine.calculate("123.4%"));
            for (int i = 0; i < 10; i++) {
                RandomTestCase test = new RandomTestCase(0);
                System.err.println(test.expression);
                Assert.assertEquals(test.result, engine.calculate(test.expression));
            }
        } catch (CalculationException | ArithmeticException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private static class RandomTestCase {
        static final Random random = new Random();

        final double result;
        final String expression;

        final char operations[] = new char[]{'+', '-', '*', '/', '^'};

        RandomTestCase(int depth) {
            RandomTestCase left, right;
            int operation = random.nextInt(6);
            if (depth > 10)
                operation = 0;
            switch (operation) {
                case 0:
                    this.result = random.nextDouble() * random.nextInt() / 65536;
                    this.expression = Double.toString(result);
                    return;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    left = new RandomTestCase(depth + 1);
                    right = new RandomTestCase(depth + 1);
                    switch (operation) {
                        case 1:
                            this.result = left.result + right.result;
                            break;
                        case 2:
                            this.result = left.result - right.result;
                            break;
                        case 3:
                            this.result = left.result * right.result;
                            break;
                        case 4:
                            this.result = left.result / right.result;
                            break;
                        case 5:
                            this.result = Math.pow(left.result, right.result);
                            break;
                        default:
                            this.result = 0;
                    }
                    this.expression = String.format("(%s)%c(%s)", left.expression, operations[operation - 1], right.expression);
                    return;
                default:
                    this.result = 0;
                    this.expression = null;
                    break;
            }
        }
    }
}
