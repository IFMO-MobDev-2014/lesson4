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
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    Random rnd = new Random();

    double randomDouble() {
        return rnd.nextDouble();
    }

    final double EPS = 1e-9;
    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        for (int i = 0; i < 10; i++) {
            double a = randomDouble();
            double b = randomDouble();
            String sa = Double.toString(a);
            String sb = Double.toString(b);
            try {
                Assert.assertEquals(true, Math.abs(CalculationEngineFactory.defaultEngine().calculate(sa + " +" + sb) - (a + b)) < EPS);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                Assert.assertEquals(true, Math.abs(CalculationEngineFactory.defaultEngine().calculate(sa + "*" + sb) - (a * b)) < EPS);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                Assert.assertEquals(true, Math.abs(CalculationEngineFactory.defaultEngine().calculate(sa + " -" + sb) - (a - b)) < EPS);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                Assert.assertEquals(true, Math.abs(CalculationEngineFactory.defaultEngine().calculate(sa + "/ " + sb) - (a / b)) < EPS);
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    /*static class Test {
        String str;
        double ans;
        Test(String str, double ans) {

        }
    }
    private*/
}
