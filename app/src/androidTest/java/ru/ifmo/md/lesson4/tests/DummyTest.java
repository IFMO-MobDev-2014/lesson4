package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.DecimalFormat;
import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    CalculationEngine engine;
    public static final double EPS = (double) 1e-7;
    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
    }

    public void assertException(String s) {
        try {
            engine.calculate(s);
            Assert.fail("No exception on " + s);
        } catch (CalculationException e) {
        }
    }


    public void check(double ans, String str) {
        try {
            Assert.assertEquals(ans, engine.calculate(str));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    public void checkForDifficult(double ans, String str) {
        try {
            Assert.assertTrue(Math.abs(ans - engine.calculate(str)) < EPS);
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testException() {
        assertException("(");
        assertException(")");
        assertException("()");
        assertException("5+");
        assertException("+5");
        assertException("5..0");
        assertException("5.0.");
        assertException("3+2(10)");
        assertException("3+2*(10+)");
        assertException(".");
        assertException("2+2+3+(3*5)+10)");
    }

    @Test
    public void testOperation() throws Exception {
        check(29D, "15+14");
        check(1D, "15-14");
        check(210D, "15*14");
        check(3D, "15/5");
    }

    @Test
    public void testFloatOpertion() throws Exception {
        check(1.2345D + 5.4321D,"1.2345+5.4321");
        check(1.2345D - 5.4321D,"1.2345-5.4321");
        check(1.2345D * 5.4321D,"1.2345*5.4321");
        check(1.2345D / 5.4321D,"1.2345/5.4321");
    }

    @Test
    public void testAssociative() throws Exception {
        check(1.2345D + 5.4321D * 1.2345D,"1.2345+5.4321*1.2345");
        check(1.2345D + 5.4321D / 1.2345,"1.2345+5.4321/1.2345");
        check(1.2345D - 5.4321D * 1.2345,"1.2345-5.4321*1.2345");
        check(1.2345D - 5.4321D / 1.2345,"1.2345-5.4321/1.2345");
        check(3D-4D+5D-6D,"3-4+5-6");
        check(3D*4D*5D,"3*4*5");
        check(3D/4D/5D,"3/4/5");
    }

    @Test
    public void testBrackets() throws Exception {
        check((1.2345D + 5.4321D) * (1.2345D + 2D),"(1.2345+5.4321)*(1.2345+2)");
        check((1.2345D + 5.4321D) / (1.2345D - 2D),"(1.2345+5.4321)/(1.2345-2)");
        check((1.2345D - 5.4321D) * (1.2345D + 2D),"(1.2345-5.4321)*(1.2345+2)");
        check((1.2345D - 5.4321D) / (1.2345D - 2D),"(1.2345-5.4321)/(1.2345-2)");
        check(3D-(4D+5D-6D),"3-(4+5-6)");
        check(3D-(4D+5D)-6D,"3-(4+5)-6");
        check(3D/(4D/(5D*5D)),"3/(4/(5*5))");
    }

    @Test
    public void testALotOfBrackets() throws Exception {
        int countBrackets = 1000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countBrackets; i++) {
            sb.append('(');
        }
        sb.append("5+5");
        for (int i = 0; i < countBrackets; i++) {
            sb.append(')');
        }
        check(10D, sb.toString());
    }

    @Test
    public void testBigRandomPlusMinus() throws Exception {
        int countTests = 100;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(10);

        for (int test = 0; test < countTests; test++) {
            int countTerms = 1000;
            StringBuilder sb = new StringBuilder();
            Random rnd = new Random();
            double MAX = 100D;
            double ans = 0;
            for (int i = 0; i < countTerms; i++) {
                double term = rnd.nextDouble() * MAX;
                int tp = rnd.nextInt(2);
                if (i != 0) {
                    if (tp == 1) {
                        sb.append('+');
                        ans += term;
                    } else {
                        sb.append('-');
                        ans -= term;
                    }
                } else {
                    ans = term;
                }
                sb.append(df.format(term).replaceAll(",","."));
            }
            checkForDifficult(ans, sb.toString());
        }
    }

    @Test
    public void testBigRandomMulDiv() throws Exception {
        int countTests = 1000;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(100);

        for (int test = 0; test < countTests; test++) {
            int countTerms = 100;
            StringBuilder sb = new StringBuilder();
            Random rnd = new Random();
            double MAX = 5D;
            double ans = 0;
            for (int i = 0; i < countTerms; i++) {
                double term = rnd.nextDouble() * MAX;
                int tp = rnd.nextInt(2);
                if (i != 0) {
                    if (tp == 1) {
                        sb.append('*');
                        ans *= term;
                    } else {
                        sb.append('/');
                        ans /= term;
                    }
                } else {
                    ans = term;
                }
                sb.append(df.format(term).replaceAll(",","."));
            }
            checkForDifficult(ans, sb.toString());
        }
    }
}
