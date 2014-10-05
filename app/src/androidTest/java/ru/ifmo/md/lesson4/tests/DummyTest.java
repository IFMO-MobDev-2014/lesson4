package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngine.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    private class Pair {
        public String expr;
        public double val;
        public Pair(String expr, double val) {
            this.expr = expr;
            this.val = val;
        }
    }

    Random rnd = new Random();
    public int randFromTo(int l, int r) {
        return l + rnd.nextInt(r - l + 1);
    }
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

    public Pair genRandomExpr(int len) {
        if (len < 5) {
            double x = 0;
            for (int i = 0; i < len; ++i)
                x = x * 10 + randFromTo(0, 9);
            return new Pair("" + x, x);
        }
        int l1 = randFromTo(len / 4, len / 2);
        Pair l = genRandomExpr(l1);
        Pair r = genRandomExpr(len - l1);
        String op = "";
        Pair ans = null;
        switch (randFromTo(0, 3)) {
            case 0:
                op = "+";
                ans = new Pair("(" + l.expr + ")" + op + "(" + r.expr + ")", r.val + l.val);
                break;
            case 1:
                op = "-";
                ans = new Pair("(" + l.expr + ")" + op + "(" + r.expr + ")", r.val - l.val);
                break;
            case 2:
                op = "*";
                ans = new Pair("(" + l.expr + ")" + op + "(" + r.expr + ")", r.val * l.val);
                break;
            case 3:
                op = "/";
                ans = new Pair("(" + l.expr + ")" + op + "(" + r.expr + ")", r.val / l.val);
                break;
        }
        return ans;
    }

    @Test
    public void lightTest() {
        CalculationEngine calculator = CalculationEngineFactory.defaultEngine();
        for (int i = 10; i <= 20; i++) {
            Pair p = genRandomExpr(i);
            double my = 0;
            System.out.println(p.expr);
            try {
                my = calculator.calculate(p.expr);
                Assert.assertEquals(p.val, my, 1e-9);
            } catch (CalculationException e) {
                Assert.assertEquals(Double.isInfinite(p.val), true);
            }
        }
    }

}
