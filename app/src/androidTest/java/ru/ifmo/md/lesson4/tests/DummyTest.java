package ru.ifmo.md.lesson4.tests;

import android.graphics.Color;
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
    private final static double eps = 1e-5;
    private final static int CNT_TEST = (int) 1e4;
    private static final int CNT_UNARY = 100;
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

    Pair<String, Double> generateUnary(char atom) {
        int k = Math.abs(rand.nextInt()) % CNT_UNARY;
        double x = rand.nextDouble();
        String temp = "";
        for (int i = 0; i < k; i++) {
            temp += atom;
        }

        temp =temp + String.format("%f", x);
        if (atom == '-' && k % 2 != 0)
            x *= -1;

        return new Pair<String, Double>(temp, x);
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

//    @Test
//    public void hard() {
//        Pair<String, Double> x;
//        int id = Math.abs(rand.nextInt());
//        Pair<String, Double> res = generateUnary(atoms.charAt(id % 2));
//        String first = res.first;
//        Double second = res.second;
//
//        for (int i = 0; i < CNT_TEST; i++) {
//            id = Math.abs(rand.nextInt());
//            char atom = atoms.charAt(id % 4);
//
//            id = Math.abs(rand.nextInt());
//            x = generateExpr(atoms.charAt(id % 4));
//            first = '(' + first + ')' + atom + '(' + x.first + ')';
//            second = retRes(second, x.second, atom);
//        }
//
//        try {
////            if (!isEqual(second, CalculationEngineFactory.defaultEngine().calculate(first)))
////                throw new CalculationException();
//            Assert.assertTrue(isEqual(second, CalculationEngineFactory.defaultEngine().calculate(first)));
//        } catch (CalculationException e) {
//                Assert.fail("Exception happened " + e);
//
////            try {
////                Assert.fail("Exception happened " + first + "  " + second + "   " + CalculationEngineFactory.defaultEngine().calculate(first));
////            } catch (CalculationException e1) {
////                e1.printStackTrace();
////            }
//        }
//    }

}
