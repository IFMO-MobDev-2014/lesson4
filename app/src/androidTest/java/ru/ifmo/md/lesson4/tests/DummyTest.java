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

@Config(emulateSdk = 17)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    CalculationEngine calculator;

    @Before
    public void setup() {
        calculator = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testWhoppingComplex() {
        shouldNotFail("5+5");
        shouldNotFail("-1");
        shouldNotFail("(50.0)-2.333*5.5");
        shouldNotFail("0-3.33/3");
        shouldNotFail("(-3-3*(((5))))");
        shouldNotFail("--3+55.44444");
        shouldNotFail("((5)*3)/(-(4.0))+55.5-3+(-3.3)");

        shouldFail("3-*3");
        shouldFail("***");
        shouldFail("(((3-5))))))");
        shouldFail("-3-");
        shouldFail("+");
        shouldFail("3.33-3.333.333");
        shouldFail("1488/(500-400-100)");
        shouldFail("()");

        shouldBeEquals("8.8/2", 4.4);
        shouldBeEquals("(((1.0+2.1+2.3)))/(13.5-4.5)", 0.6);
        shouldBeEquals("1000000.55555*(((6-1-2-3)))", 0.0);
        shouldBeEquals("44*6-((5)-(2*2))/2.234+3.0/3.00000*((4.4))*(-333)", -1201.64762757);
        shouldBeEquals("3*6/0.22-555/(7.02-8.22)+(((555)))/(((((99)))+(-94)))", 655.318181818);

    }

    public void shouldFail(String expression) {
        boolean fail = false;
        try {
            calculator.calculate(expression);
        } catch (CalculationException e) {
            fail = true;
        }
        if (!fail) {
            Assert.fail("Fail expected: "+expression);
        }
    }

    public void shouldNotFail(String expression) {
        try {
            calculator.calculate(expression);
        } catch (CalculationException e) {
            Assert.fail("Fail didn't expect: " + expression);
        }
    }

    public void shouldBeEquals(String expression, double result) {
        try {
            Assert.assertEquals(calculator.calculate(expression), result, 0.0000001);
        } catch (CalculationException e) {
            Assert.fail("Exception happened: " + expression);
        }
    }
}
