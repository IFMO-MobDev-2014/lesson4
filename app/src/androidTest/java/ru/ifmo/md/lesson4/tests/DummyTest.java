package ru.ifmo.md.lesson4.tests;

import android.util.Pair;

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

    private CalculationEngine engine;

    protected void assertFail(String expr) {
        try {
            engine.calculate(expr);
            Assert.fail("not failed on " + expr);
        } catch (CalculationException e) {
        }
    }

    protected void assertEqual(String expr, double result) {
        try {
            if (engine.calculate(expr) == result || Math.abs(engine.calculate(expr) - result) < 1.E-6) {
                return;
            }
            Assert.fail(expr + " != " + result + ", diff: " + (engine.calculate(expr) - result));
        } catch (CalculationException e) {
            Assert.fail("failed on " + expr);
        }

    }

    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
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
    public void testParser() {
        assertFail("0 0");
        assertFail("((");
        assertFail("()");
        assertFail("(+)");
        assertFail("(");
        assertFail("(-3)(-3)");
        assertFail("-(3)-");
        assertFail("-3-3,0");
        assertFail("-3(+2)");
        assertFail("*7*(3+3)");
        assertFail(") + (3)");
        assertFail("(7+(7+3)");
        assertFail("9-");
        assertFail("(6)(8)");
        assertFail("1-1(4)");
        assertFail("7.0.0");
        assertFail("(");
        assertFail("(-.-)");
        assertFail(".");

        assertEqual("-(+(-(+7)))", 7.);
        assertEqual("1/-5", -0.2);
        assertEqual("72.0/532*729*177/699-267.0*471/481",
                72.0 / 532 * 729 * 177 / 699 - 267.0 * 471 / 481);
        assertEqual("-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))",
                -(+(-18.0 / 36 * (10.0 + 13.0 / 36 / 16)) + (-21.0 / 7 * 24 + 21.0 * 41 + 6.0)));

    }

    static public Pair<String, Double> generateExpression(Random r, int maxDepth) {
        --maxDepth;
        int choice = r.nextInt(7);
        if (maxDepth == 0 || choice == 0) {
            // const
            double num = r.nextInt(1000) / 10.0; // [0.0, 100.0], so that calculation wont explode
            return new Pair<String, Double>(String.valueOf(num), num);
        } else if (choice == 1) { // unary minus
            Pair<String, Double> p = generateExpression(r, maxDepth);
            return new Pair<String, Double>("-" + p.first, -p.second);
        } else if (choice == 2) {
            // unary plus
            Pair<String, Double> p = generateExpression(r, maxDepth);
            return new Pair<String, Double>("+" + p.first, p.second);

        } else {
            Pair<String, Double> e1 = generateExpression(r, maxDepth);
            Pair<String, Double> e2 = generateExpression(r, maxDepth);
            double result1 = e1.second, result2 = e2.second, result = 0.;
            String op = "";
            switch (choice) {
                case 3:
                    op = "-";
                    result = result1 - result2;
                    break;
                case 4:
                    op = "+";
                    result = result1 + result2;
                    break;
                case 5:
                    op = "*";
                    result = result1 * result2;
                    break;
                case 6:
                    op = "/";
                    result = result1 / result2;
                    break;
                default:
                    assert false;
            }
            return new Pair<String, Double>("(" + e1.first + op + e2.first + ")", result);
        }
    }

    @Test
    public void testRandomExpr() {
        Random rand = new Random(2014);
        final int numIterations = 100000, maxDepth = 15;
        for (int i = 0; i != numIterations; i++) {
            Pair<String, Double> expr = generateExpression(rand, maxDepth);
            if (Double.isNaN(expr.second)) { // exploded
                continue;
            }
            assertEqual(expr.first, expr.second);
        }
    }

}