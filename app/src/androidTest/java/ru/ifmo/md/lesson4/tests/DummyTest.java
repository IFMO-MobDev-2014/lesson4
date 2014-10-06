package ru.ifmo.md.lesson4.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.engine.CalculationEngine;
import ru.ifmo.md.lesson4.engine.CalculationEngineFactory;
import ru.ifmo.md.lesson4.exception.CalculationException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    // Acceptable absolute error
    public static final double EPS = 1e-9;

    private CalculationEngine engine;

    void doubleAssertEquals(double a, double b) {
        assertTrue(Math.abs(a - b) < EPS);
    }

    void assertNotFail(String expression) {
        try {
            engine.calculate(expression);
        } catch (Exception e) {
            fail(e + " thrown in method body");
        }
    }

    void assertFail(String expression) {
        try {
            engine.calculate(expression);
        } catch (CalculationException e) {
            return;
        } catch (Exception e) {
            fail(e + " thrown in method body");
        }

        fail("Method should throw exception");
    }

    void assertResult(double result, String expression) {
        try {
            doubleAssertEquals(result, engine.calculate(expression));
        } catch (Exception e) {
            fail(e + " throw in method body");
        }
    }

    @Before
    public void setup() {
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testParsing() throws Exception {
        assertFail("-");
        assertFail("abacaba");
        assertFail("");
        assertFail("1E");
        assertFail("123-123-123+");
        assertFail("123123*+2");
        assertFail("1+(2+(3+4)+5");
        assertFail("1+2+3*(5))");

        assertNotFail("-5");
        assertNotFail("1.5E-20");
        assertNotFail("123-123-123+3");
        assertNotFail("123123*(-2)");
        assertNotFail("1+(2+(3+4)+5)");
        assertNotFail("(1+2+3*(5))");
    }

    @Test
    public void testCorrectness() throws Exception {
        assertResult(123, "123");
        assertResult(-123, "-123");
        assertResult(34E9, "34E9");
        assertResult(Double.MAX_VALUE, "1.79769313486231570E+308");
        assertResult(Double.MIN_VALUE, "5E-324");

        assertResult(123 + 45, "123+45");
        assertResult(123 - 45, "123-45");
        assertResult(123 * 45, "123*45");
        assertResult(123.0 / 45, "123/45");
        assertResult(123 + 456 * (789 - 987.0 / (23 - 11)), "123+456*(789-987/(23-11))");
        assertResult(5 * (100 + 12.0 / 24 + 38.123 * 100 + 26 + (15.123E10 - 10) * (6 + 24 + 8)),
                "5*(100+12/24+38.123*100+26+(15.123E10-10)*(6+24+8))");
        assertResult(123456789.0 * 987654321, "123456789*987654321");

        assertEquals(Double.POSITIVE_INFINITY, engine.calculate("1/0"));
        assertEquals(Double.NEGATIVE_INFINITY, engine.calculate("-1/0"));
        assertEquals(Double.NaN, engine.calculate("0/0"));
    }
}
