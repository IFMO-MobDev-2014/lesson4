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

    private void expectEqual(String expression, double result) {
        try {
            double res = CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.assertEquals(result, res);
        } catch (CalculationException e) {
            Assert.fail("Exception happend: " + e);
        }
    }

    private void expectError(String expression) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.fail("Must fail, but didn't actually: " + expression);
        } catch (CalculationException e) {
        }
    }

    private void expectNoError(String expression) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expression);
        } catch (CalculationException e) {
            Assert.fail("Must not fail, but did actually");
        }
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
    public void testEqual() {
        expectEqual("1+2", 3d);
        expectEqual("2+2*2", 6d);
        expectEqual("1.0+2.0", 3d);
        expectEqual("1.0+2", 3d);
        expectEqual("5*5", 25d);
        expectEqual("5*5+5/5", 26d);
    }

    @Test
    public void testError() {
        expectError("1-");
        expectError("---");
        expectError("-");
        expectError("+");
        expectError("-");
        expectError("*");
        expectError("/");
        expectError(".");
        expectError("(");
        expectError(")");
        expectError(")+");
        expectError(")6");
        expectError("5(");
        expectError("((");
        expectError("5*(");
    }

    @Test
    public void testNoError() {
        expectNoError("1");
        expectNoError("0.");
        expectNoError("1.");
        expectNoError(".0");
        expectNoError(".0");
        expectNoError("1/0");
        expectNoError("2/0");
        expectNoError("2+2");
        expectNoError("2-2");
        expectNoError("2*2");
        expectNoError("2/2");
        expectNoError("2/1.");

    }

}
