package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    Random rnd;
    int NUM;

    @Before
    public void setup() {
        rnd = new Random(123123123);
        NUM = 100;
    }

    private void testEquals(String str, double res) {
        try {
            Assert.assertTrue(Math.abs(res - CalculationEngineFactory.defaultEngine().calculate(str)) < 1E-9);
        } catch (CalculationException e) {
            Assert.fail("not equals!" + e);
        }
    }

    private void testOnFail(String str) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(str);
            Assert.fail("not failed!" + str);
        } catch (CalculationException e) {
        }
    }

    @Test
    public void testWhoppingComplex() {
        testEquals("0-0", 0d);
        testEquals("(1+2)+3", 6d);
        testEquals("-(4)+5", 1d);
        testEquals("6*7", 42d);
        testEquals("(8+9)*10", 170d);
        testEquals("11+12*13", 167d);
        testEquals("14-(-15)", 29d);
        testEquals("16*(-17)", -272d);
        testEquals("1-1*(-1)", 2d);
        testEquals("2+(+2)", 4d);
        testEquals("++1", 1d);
        testEquals("6/2", 3d);
    }

    @Test
    public void testFails() {
        testOnFail(")");
        testOnFail("()");
        testOnFail("0.0.0");
        testOnFail(".");
        testOnFail("1/0");
        testOnFail("1/(1-1)");
        testOnFail("0/0");
        testOnFail("+");
        testOnFail("/123*");
        testOnFail("yo");
    }

    @Test
    public void testRandom() {
        String expr = "+(123.321*x/x*x)-((+(-0.321))+x-1*123)*(123*x-312/x)";
        for (int i = 0; i < NUM; i++) {
            double x = rnd.nextDouble();
            double expected = +(123.321*x/x*x)-((+(-0.321))+x-1*123)*(123*x-312/x);
            String str = expr.replace("x", String.valueOf(x));
            testEquals(str, expected);
        }
    }
}
