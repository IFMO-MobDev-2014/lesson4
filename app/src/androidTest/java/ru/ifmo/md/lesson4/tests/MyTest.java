package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MyTest {
    CalculationEngine calc = CalculationEngineFactory.defaultEngine();
    Random rand = new Random(0);

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testWhoppingComplex() {
        for (int i=0; i<10; i++) {
            Double a = rand.nextDouble();
            Double b = rand.nextDouble();
            String sa = Double.toString(a);
            String sb = Double.toString(b);
            expect(a + "+" + b, a+b);
            expect(a + "-" + b, a-b);
            expect(a + "*" + b, a * b);
            expect(a + "/" + b, a / b);
        }
        expect("64.", 64.);
        expect(".29", 0.29);
        expect("(2+3.5)*7-(19)", (2.+3.5)*7.-19.);
        expect("89.15---43.8", 89.15-43.8);
        expect("2.2-(+(+3.2))", 2.2-3.2);
        expect("(((8.9)))", 8.9);
        String[] invalid = new String[] {
                "+", "--", "a", "2+", "(())", "32*", ".", "2-.", "3 + 3", " ", "(4+)5",
                ")12", "12.+.", "((2+3)*9)-(", "(-)"
        };
        for (String expr : invalid) {
            fail(expr);
        }
    }

    private void expect(String expr, double expected) {
        try {
            Assert.assertEquals(expected, calc.calculate(expr));
        } catch (CalculationException e) {
            Assert.fail("Exception while calculating '" + expr +"'");
        }
    }

    private void fail(String expr) {
        try {
            calc.calculate(expr);
            Assert.fail("Expected not happened while calculating '" + expr + "'");
        } catch (CalculationException e) {
        }
    }
}
