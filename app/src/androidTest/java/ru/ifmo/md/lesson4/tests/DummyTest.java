package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    private void shouldEquals(double result, String expression) {
        try {
            Assert.assertTrue(Math.abs(result - CalculationEngineFactory.defaultEngine().calculate(expression)) < 1E-9);
        } catch (CalculationException e) {
            Assert.fail("Exception happened: " + e);
        }
    }

    private void shouldGetError(String expression) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.fail("Not failed: " + expression);
        } catch (CalculationException e) {
        }
    }

    private void shouldNotGetError(String expression) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expression);
        } catch (CalculationException e) {
            Assert.fail("Exception happened: " + e);
        }
    }

    @Test
    public void testEquals() {
        shouldEquals(10d, "5+5");
        shouldEquals(10d, "15-5");
        shouldEquals(10d, "2*5");
        shouldEquals(10d, "100/10");
        shouldEquals(10d, "2+3+5");
        shouldEquals(6d, "2+2*2");
        shouldEquals(6d, "2.0+2.*2.0");
        shouldEquals(6d, "1.5*4");
        shouldEquals(6d, "18.6/3.1");
        shouldEquals(3d, "(----3)");
        shouldEquals(8d, "(2+2)*2");
        shouldEquals(-4.2, "2+3+5.7-4.3*2-2.1*3");
    }

    @Test
    public void testErrors() {
        shouldGetError("fail");
        shouldGetError("(2+3))");
        shouldGetError("2///3");
        shouldGetError("2+*/=4");
        shouldGetError("2+(4-)");
        shouldGetError("2+(4-3..)");
        shouldGetError("2+(4-.3.0)");
    }

    @Test
    public void testNotErrors() {
        shouldNotGetError("1/(1-1)");
    }

}
