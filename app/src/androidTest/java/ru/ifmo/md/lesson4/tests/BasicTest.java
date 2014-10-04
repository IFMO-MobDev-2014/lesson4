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
public class BasicTest {

    private CalculationEngine calc;

    @Before
    public void doSetup() {
        calc = CalculationEngineFactory.defaultEngine();
    }

    // tests from year2013 with a few added and/or modified to be in line with current implementation of calc

    @Test
    public void testCorrectness() {
        expectEqual("(--3)", 3.0);
        expectEqual("-(+(-(+7)))", 7.0);
        expectEqual("1/-5", -0.2);
        expectEqual("72.0/532*729*177/699-267.0*471/481", -236.466070483464);
        expectEqual("-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))", -789.988715277778);
        expectEqual("-25/(21-7*3)", Double.NEGATIVE_INFINITY);
        expectEqual("7-.1", 6.9);
        expectEqual("-3-(-3)", 0.0);
        expectEqual("-(-(-8))", -8.0);
        expectEqual("-3", -3.0);
        expectEqual("+3", 3.0);
        expectEqual("9.", 9.0);
        expectEqual("1/0", Double.POSITIVE_INFINITY);
        expectEqual("0/0", Double.NaN);
    }

    @Test
    public void testFailures() {
        expectFail("()");
        expectFail("((");
        expectFail("(");
        expectFail(")");
        expectFail("(+)");
        expectFail("(-3)(-3)");
        expectFail("-(3)-");
        expectFail("-3(+2)");
        expectFail("*7*(3+3)");
        expectFail(")+(3)");
        expectFail("(7+(7+3)");
        expectFail("9-");
        expectFail("(6)(8)");
        expectFail("1-1(4)");
        expectFail("3)");
        expectFail("(3");
        expectFail("7.0.0");
        expectFail("(");
        expectFail("(-.-)");
        expectFail(".");
    }

    public void expectFail(String s) {
        try {
            calc.calculate(s);
            Assert.fail("Unable to fail on " + s);
        } catch (CalculationException ex) {
            // ok
        }
    }

    public void expectEqual(String s, double d) {
        try {
            Assert.assertEquals(calc.calculate(s), d, 1e-10);
        } catch (CalculationException ex) {
            Assert.fail("Unexpected exception: " + ex.getMessage());
        }
    }

}
