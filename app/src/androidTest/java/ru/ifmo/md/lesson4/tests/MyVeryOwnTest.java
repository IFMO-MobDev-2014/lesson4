package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

import java.text.DecimalFormat;
import java.util.Random;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MyVeryOwnTest {
    private static final int N = 100;
    private static final double eps = 10e-7;

    @Test
    public void arithmeticAdd() {
        Random rand = new Random(System.nanoTime());
        double a, b;
        DecimalFormat df = new DecimalFormat("################.################");
        for (int i = 0; i < N; i++) {
            a = Double.valueOf(df.format(rand.nextDouble()));
            b = Double.valueOf(df.format(rand.nextDouble()));
            try {
                Assert.assertEquals(a + b, CalculationEngineFactory.defaultEngine().calculate(df.format(a) + " + " + df.format(b)), eps);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test
    public void arithmeticSub() {
        Random rand = new Random(System.nanoTime());
        double a, b;
        DecimalFormat df = new DecimalFormat("################.################");
        for (int i = 0; i < N; i++) {
            a = Double.valueOf(df.format(rand.nextDouble()));
            b = Double.valueOf(df.format(rand.nextDouble()));
            try {
                Assert.assertEquals(a - b, CalculationEngineFactory.defaultEngine().calculate(df.format(a) + " - " + df.format(b)), eps);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test
    public void arithmeticMul() {
        Random rand = new Random(System.nanoTime());
        double a, b;
        DecimalFormat df = new DecimalFormat("################.################");
        for (int i = 0; i < N; i++) {
            a = Double.valueOf(df.format(rand.nextDouble()));
            b = Double.valueOf(df.format(rand.nextDouble()));
            try {
                Assert.assertEquals(a * b, CalculationEngineFactory.defaultEngine().calculate(df.format(a) + " * " + df.format(b)), eps);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test
    public void arithmeticDiv() {
        Random rand = new Random(System.nanoTime());
        double a, b;
        DecimalFormat df = new DecimalFormat("################.################");
        for (int i = 0; i < N; i++) {
            a = Double.valueOf(df.format(rand.nextDouble()));
            b = Double.valueOf(df.format(rand.nextDouble()));
            try {
                Assert.assertEquals(a / b, CalculationEngineFactory.defaultEngine().calculate(df.format(a) + " / " + df.format(b)), eps);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test(expected = CalculationException.class)
    public void DBZTest() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("1 / 0");
    }

    @Test(expected = CalculationException.class)
    public void wrongBracketsTest0() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("((1 + 2)");
    }

    @Test(expected = CalculationException.class)
    public void wrongBracketsTest1() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate(")(1 + 2)");
    }

    @Test(expected = CalculationException.class)
    public void wrongBracketsTest2() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("1 + 2(");
    }

    @Test(expected = CalculationException.class)
    public void wrongBracketsTest3() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("())(1 + 2)(");
    }

    @Test(expected = CalculationException.class)
    public void wrongBracketsTest4() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("(1 + 2");
    }

    @Test
    public void neg() {
        try {
            Assert.assertEquals(-.1, CalculationEngineFactory.defaultEngine().calculate("-(-(-0.1))"), .0);
        } catch (CalculationException e) {
            Assert.fail();
        }
    }

    @Test(expected = CalculationException.class)
    public void wrongSymbols0() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("-1e10");
    }

    @Test(expected = CalculationException.class)
    public void wrongSymbols1() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("1 * 2 * 3 * x");
    }

    @Test(expected = CalculationException.class)
    public void wrongSymbols2() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("((1 * 2) + (-3)) = ((1 * 2) + (-3))");
    }

    @Test
    public void checkFormat() {
        try {
            Assert.assertEquals(.1, CalculationEngineFactory.defaultEngine().calculate(".1"), .0);
        } catch (CalculationException e) {
            Assert.fail();
        }
    }
}
