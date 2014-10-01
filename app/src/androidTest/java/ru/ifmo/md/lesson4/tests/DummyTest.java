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
public class DummyTest {
    private static double eps = 1e-9;
    private static Random rng = new Random(58L);

    private CalculationEngine engine;

    private void expectToFailOn(String expression) {
        boolean didNotFail = true;
        try {
            System.out.println("Testing " + expression);
            engine.calculate(expression);
        } catch (CalculationException e) {
            didNotFail = false;
        } finally {
            if (didNotFail) {
                Assert.fail("Should have failed on test " + expression);
            }
        }
    }

    private void shouldNotFailOn(String expression) {
        try {
            System.out.println("Testing " + expression);
            engine.calculate(expression);
        } catch (CalculationException e) {
            Assert.fail("Should have not failed on test " + expression);
        }
    }

    private void shouldReturn(double result, String expression) {
        try {
            System.out.println("Testing " + expression);
            Assert.assertEquals(result, engine.calculate(expression), eps);
        } catch (CalculationException e) {
            Assert.fail("Should not have failed on test " + expression);
        }
    }

    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testParser() {
        expectToFailOn("0 0");
        expectToFailOn("()");
        expectToFailOn("((");
        expectToFailOn("(+)");
        expectToFailOn("(");
        expectToFailOn("(-3)(-3)");
        expectToFailOn("(-3)-");
        expectToFailOn("-3-3,0");
        expectToFailOn("-3(+2)");
        expectToFailOn("*7*(3+3)");
        expectToFailOn(") + (3)");
        expectToFailOn("(7 + (7 + 3)");
        expectToFailOn("9-");
        expectToFailOn("(6)(8)");
        expectToFailOn("1-1(4)");
        expectToFailOn("72,0/532*729*177/699-267,0*471/481");
        expectToFailOn("(3");
        expectToFailOn("3)");
        expectToFailOn("7.0.0");
        expectToFailOn("(");
        expectToFailOn(".");
        expectToFailOn("(-.-)");
        expectToFailOn("eee");
        expectToFailOn("e5");
        expectToFailOn("5e");
        expectToFailOn("-e");
        expectToFailOn("3.14zza");
        expectToFailOn("andrewzta");

        shouldNotFailOn("9.");
        shouldNotFailOn("7-.1");
        shouldNotFailOn("(--3)");
        shouldNotFailOn("-(+(-(+7)))");
        shouldNotFailOn("+123");
        shouldNotFailOn("1/-5");
        shouldNotFailOn("-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))");
    }

    @Test
    public void testBasicArithmetic() {
        shouldReturn(10.0, "5+5");
        shouldReturn(0.0, "-3-(-3)");
        shouldReturn(-8.0, "-(-(-8))");
        shouldReturn(-430.5, "  3.5*  -123");
        shouldReturn(-.00001, "2e-5/  -  2.000000000000");
        shouldReturn(8.57682, "2.718*3.14+-(--0.577-1   )/10");
        shouldReturn(497.8753, "-(-(-\t\t-5 + 16   *55*0.55) + 1 * 2.1247) -(((-11)))");
        shouldReturn(4.0, "((2+2))-0/(--2)*555.555");
        shouldReturn(5.02, "((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((0.02 + 2. + (-10*-.3)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");

        shouldReturn(Double.NEGATIVE_INFINITY, "-25/(21-7*3)");
    }

    @Test
    public void testRandomArithmetic() {
        RandomTestGenerator testGen = new RandomTestGenerator(rng);
        for (int i = 0; i < 20; i++) {
            RandomTestGenerator.TestCase test = testGen.generateExpression(i);
            shouldReturn(test.result, test.expression);
        }
    }
}
