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

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private void expectEquals(String test, double expectingResult) {
        try {
            Assert.assertEquals(expectingResult,
                    CalculationEngineFactory.defaultEngine().calculate(test));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private void expectFail(String test) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(test);
            Assert.fail("Expected fail, but " + test);
        } catch (CalculationException e) {

        }
    }

    @Test
    public void testEqual() {
        expectEquals("0", 0.);
        expectEquals("0+0", 0.);
        expectEquals("1*0", 0.);
        expectEquals("2+2", 4.);
        expectEquals("2*2", 4.);
        expectEquals("--1", 1.);
        expectEquals("1--1", 2.);
        expectEquals("1+1+1+1+1+1", 6.);
        expectEquals("1/10000000", 1. / 10000000);

        expectEquals("456-4564*454/4546*(545+546/454)/(45-54*(545*65/54)-54)", 463.02596001399627);
        expectEquals("46*546-546+4654-4546*(5464+454645-6545/5432)", -2091620812.538659794);

        expectEquals("0/0", 0. / 0);
        expectEquals("(((((5)))))", 5.);
        expectEquals("(1.1)", 1.1);
        expectEquals("(0.+.0)", 0.);
        expectEquals("(0.0)", 0.);
    }

    @Test
    public void testFail() {
        expectFail("-1-");
        expectFail("1--1+-1-");
        expectFail("1(1)1");
        expectFail("1..00");
        expectFail("(31231+21321");
        expectFail("");
        expectFail(")");
        expectFail("1++");
        expectFail("1()");

        expectFail("(*.*)");
        expectFail("(+.+)");
        expectFail("\\(0.0)");
        expectFail("(0.0))))))))))-");
        expectFail("0/");
    }
}
