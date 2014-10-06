package ru.ifmo.md.lesson4.tests;

import android.util.Pair;

import junit.framework.Assert;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    private static int MAGIC_CONSTANT = 5; // if the constant is greater than 5, the tests will not pass
    private static double eps = 1e-5;
    private static CalculationEngine exp;
    private Random rand = new Random();

    private void equalCheck(double value, String expr) {
        try {
            Assert.assertEquals(value, exp.calculate(expr));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private void exceptionCheck(String expr) {
        boolean wasException = false;
        try {
            System.out.println(exp.calculate(expr));
        } catch (CalculationException e) {
            wasException = true;
        } finally {
            if (!wasException)
                Assert.fail("Exception not found on test:" + expr);
        }
    }

    @Before
    public void setup() {
        exp = CalculationEngineFactory.defaultEngine();
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
    public void exceptionTest() {
        exceptionCheck("2..0");
        exceptionCheck("2+3+");
        exceptionCheck("2//3");
        exceptionCheck("(2+3");
        exceptionCheck("2+3)");
        exceptionCheck("*2*3");
        exceptionCheck("2+()+3");
        exceptionCheck("2(");
        exceptionCheck("1+qwerty");
        exceptionCheck("(f)");
    }

    private Pair<Double, String> genExpr(int cnt) {
        if (cnt == 0) {
            double d = rand.nextDouble();
            return new Pair<Double, String>(d, Double.toString(d));
        }
        Pair<Double, String> f = genExpr(cnt - 1);
        Pair<Double, String> s = genExpr(cnt - 1);
        switch (rand.nextInt(6)) {
                case 0:
                    return new Pair<Double, String>(f.first + s.first,
                           "(" + f.second + "+" + s.second + ")");
                case 1:
                    return new Pair<Double, String>(f.first - s.first,
                            "(" + f.second + "-" + s.second + ")");
                case 2:
                    return new Pair<Double, String>(f.first * s.first,
                            "(" + f.second + "*" + s.second + ")");
                case 3:
                    return new Pair<Double, String>(f.first / s.first,
                            "(" + f.second + "/" + s.second + ")");
                case 4:
                    return new Pair<Double, String>(-f.first,"(-" + f.second + ")");
                case 5:
                    return new Pair<Double, String>(f.first, "(+" + f.second + ")");
            default:
                    return null;
            }
    }

    @Test
    public void notSoSimpleTest() {
        for (int i = 0; i < MAGIC_CONSTANT; i++) {
            Pair<Double, String> f = genExpr(i);
            System.out.println(f.first + ":" + f.second);
            equalCheck(f.first, f.second);
        }
    }

}
