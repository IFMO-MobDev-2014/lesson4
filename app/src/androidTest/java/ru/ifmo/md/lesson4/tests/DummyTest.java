package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

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
    CalculationEngine engine;
    private static final double EPS = 1E-8;

    @Before
    public void setup() {
        engine = CalculationEngineFactory.defaultEngine();
    }

    private void doesWork(double answer, String expression) {
        try {
            double result = engine.calculate(expression);
            Assert.assertTrue("expression " + expression + " should be equal to "
                + answer + ", but it is " + result
                , Double.isNaN(result) && Double.isNaN(answer)
               || (Double.isInfinite(result) && Double.isInfinite(answer) && result == answer)
               || Math.abs(result - answer) < EPS);
        } catch (CalculationException e) {
            System.out.println("It fails with" + e);
            Assert.fail("Exception " + e + " on expression: " + expression);
        }
    }

    private void doesFail(String expression) {
        try {
            engine.calculate(expression);
            Assert.fail("Should fail on expression: " + expression);
        } catch (CalculationException e) {
        }
    }

    @Test
    public void tests() {
                doesWork(0d, "5 - 5");
                doesWork(5d, "----5");
                doesWork(Double.POSITIVE_INFINITY, "5/0");
                doesWork(5d, "((((((((((((((((((((((((((5))))))))))))))))))))))))))");
                doesFail("0 0");//spaces are impossible in the app
                doesFail("()");//impossible in the app
                doesFail("((");
                doesFail("(+)");//impossible in the app
                doesFail("(");
                doesFail("(-3)(-3)");//impossible in the app
                doesWork(3d, "--3");
                doesFail("-(3)-");//impossible in the app
                doesFail("-3-3,0");//impossible in the app
                doesFail("-3(+2)");//impossible in the app
                doesFail("*7*(3+3)");//impossible in the app
                doesFail(")  (3)");//impossible in the app
                doesFail("(7(7+3)");
                doesFail("9-");
                doesWork(7d, "-(+(-(+7)))");
                doesWork(-0.2d, "1/-5");
                doesFail("(6)(8)");//impossible in the app
                doesFail("1-1(4)");
                doesWork(-236.4660704834d, "72.0/532*729*177/699-267.0*471/481");
                doesWork(-789.988715277778, "-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))");
                doesWork(Double.NEGATIVE_INFINITY, "-25/(21-7*3)");
                doesFail("3)");//impossible in the app
                doesFail("(3");
                doesWork(6.9d, "7-.1");
                doesFail("7.0.0");
                doesWork(0, "-3-(-3)");
                doesWork(-8, "-(-(-8))");
                doesFail("(-.-)");//impossible in the app
                doesFail(".");
                doesWork(-3, "-3");
                doesWork(3, "+3");
                doesWork(9, "9.");
    }
}
