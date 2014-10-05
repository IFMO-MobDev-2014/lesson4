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
public class DummyTest {

    Random rand = new Random();

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
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("4.4+0.3+5.3"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        for (int i = 0; i < 100000; i++) {
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals(a * b, CalculationEngineFactory.defaultEngine().calculate(Double.toString(a) + "   *   " + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals(a / b, CalculationEngineFactory.defaultEngine().calculate(Double.toString(a) + " /   " + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals(a + b, CalculationEngineFactory.defaultEngine().calculate(Double.toString(a) + " --  " + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals(a + b, CalculationEngineFactory.defaultEngine().calculate(Double.toString(a) + "  ++   " + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals(a - b, CalculationEngineFactory.defaultEngine().calculate(Double.toString(a) + "---" + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals((-a) * b, CalculationEngineFactory.defaultEngine().calculate("-" + Double.toString(a) + "   *   " + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
            try {
                double a = rand.nextDouble();
                double b = rand.nextDouble();
                Assert.assertEquals(a * b, CalculationEngineFactory.defaultEngine().calculate(" --  \t\n" + Double.toString(a) + "   *   " + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test
    public void randomTesting() {
        String expression = "";
        double ans = 0;
        for (int i = 0; i < 1000; i++) {
            int n = rand.nextInt(4);
            double a = rand.nextDouble();
            double b = rand.nextDouble();
            double c = rand.nextDouble();
            if (n == 0) {
                expression = "(   " + expression + "  + " + Double.toString(a) + ") " + "*" + Double.toString(b);
                ans += a;
                ans *= b;
            } else if (n == 1) {
                expression = "(   " + expression + "-" + Double.toString(a) + ") " + "/" + Double.toString(b);
                ans -= a;
                ans /= b;
            } else if (n == 2) {
                expression = "(   " + expression + "  + " + Double.toString(a) + ") " + "*   (" + Double.toString(b) + "--" + Double.toString(c) + "   )";
                ans += a;
                ans *= (b + c);
            } else if (n == 3) {
                expression = "( " + expression + ") " + "*" + Double.toString(a) + "/" + Double.toString(b);
                ans *= a;
                ans /= b;
            }
            try {
                Assert.assertEquals(ans, CalculationEngineFactory.defaultEngine().calculate(expression));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test
    public void mustFailed() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("1 +");
            Assert.fail("must fail on 1 + ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("1 + (");
            Assert.fail("must fail on 1 + ( ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("1 + 2)");
            Assert.fail("must fail on 1 + 2) ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("*");
            Assert.fail("must fail on * ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("1..0");
            Assert.fail("must fail on 1..0 ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("1.1.1");
            Assert.fail("must fail on 1.1.1 ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("..");
            Assert.fail("must fail on .. ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("()");
            Assert.fail("must fail on () ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("(");
            Assert.fail("must fail on ( ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate(")");
            Assert.fail("must fail on ) ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("1 + 1()");
            Assert.fail("must fail on 1 + 1() ");
        } catch (CalculationException e) {}
        try {
            CalculationEngineFactory.defaultEngine().calculate("1 + 1(1)");
            Assert.fail("must fail on 1 + 1(1)");
        } catch (CalculationException e) {}
    }
}

