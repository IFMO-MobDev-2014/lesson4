package ru.ifmo.md.lesson4.tests;

import android.util.Pair;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    private final static double eps = 1e9;
    private final static int CNT_TEST = (int) 1e5;
    private static final int CNT_UNARY = 1000;
    private Random rand = new Random();
    public static final String atoms = "+-*/";
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

    double retRes(double x, double y, char atom) {
        switch (atom) {
            case '-':
                return x - y;
            case '*':
                return  x * y;
            case '/':
                return x / y;
            case '+':
                return x + y;
        }
        return 0;
    }

    Pair<String, Double> generateExpr(char atom) {
        double x = rand.nextDouble();
        double y = rand.nextDouble();

        return new Pair<String, Double>(String.format("%f", x) + atom + String.format("%f", y), retRes(x, y, atom));
    }

    int k2;
    double x2;
    Pair<String, Double> generateUnary(char atom) {
        int k = Math.abs(rand.nextInt()) % CNT_UNARY;
        double x = rand.nextDouble();
        String temp = "";
        for (int i = 0; i < k; i++) {
            temp += atom;
        }

        x2 = x;
        if (atom == '-' && k % 2 != 0)
            x *= -1;
        k2 = k;

        return new Pair<String, Double>(temp + String.format("%f", x), x);
    }
    boolean isEqual(double x1, double x2) {
        return Math.abs(x1 - x2) < eps;
    }

    @Test
    public void light() {
        Pair<String, Double> x;
        int id = Math.abs(rand.nextInt());
        for (int i = 0; i < CNT_TEST; i++) {
            if (rand.nextInt() % 2 == 0)
                x = generateExpr(atoms.charAt(id % 4));
            else
                x = generateUnary(atoms.charAt(id % 2));

            try {
                Assert.assertTrue(isEqual(x.second, CalculationEngineFactory.defaultEngine().calculate(x.first)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e);
            }
        }
    }

    @Test
    public void hard() {

    }

}
