package ru.ifmo.md.lesson4.tests;

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
public class MyTest {
    Random rnd = new Random();

    @Before
    public void setup() {
    }

    // made to avoid precision problems and x.xE-x notation
    private Double getSafeDouble(){
        double d = rnd.nextDouble();
        d *= 100000;
        d = Math.round(d);
        d /= 100000;
        while( d != 0.0 && Math.round(d) == 0)
        {
            d *= 10;
        }
        return d;
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        for(int i = 0; i < 10; ++i) {
            double a = getSafeDouble();
            double b = getSafeDouble();
            try {

                Assert.assertEquals(a + b, CalculationEngineFactory.defaultEngine().calculate(Double.toString(a) + " +" + Double.toString(b)));
            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e + Double.toString(a) + " " + Double.toString(b));
            }
        }
        try {
            double a = getSafeDouble();
            double b = getSafeDouble();
            double c = getSafeDouble();
            Assert.assertEquals(-(-(5 + 16 * a * b) + c) + 11, CalculationEngineFactory.defaultEngine()
                    .calculate("-(-(-\t\t-5 + 16   *"+Double.toString(a)+"*"+Double.toString(b)+") + 1 * "+Double.toString(c)+") -(((-11)))"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

    }

    static class Expr {
        String expr;
        Double answer;

        Expr(String expr, Double answer) {
            this.expr = expr;
            this.answer = answer;
        }
    }



    @Test
    public void randomTests(){
        Expr test;

        for (int i = 0; i < 30; ++i) {
            test = genExpression(0);
            try {
                if(test.answer != CalculationEngineFactory.defaultEngine().calculate(test.expr))
                {
                    Assert.fail(test.answer.toString() + "   " +
                            CalculationEngineFactory.defaultEngine().calculate(test.expr) + "   " + test.expr);
                }

            } catch (CalculationException e) {
                Assert.fail("Exception happened " + e + test.expr);
            }

        }

    }



    private Expr genExpression(int depth){
        Expr e;
        double t = getSafeDouble();
        for(int i = 0; i < rnd.nextInt(4); ++i)
        {
            t *= 10;
        }
        e = new Expr(Double.toString(t), t);

        if(depth < 3){
            int tmp = rnd.nextInt(6);
            Expr e1 = genExpression(depth + 1);
            if(tmp == 5 || tmp == 6)
            {
                e.answer = e1.answer;
                e.expr = e1.expr;
            } else {
                Expr e2 = genExpression(depth + 1);
                switch (tmp) {
                    case 0:
                        e.answer = e1.answer + e2.answer;
                        e.expr = e1.expr + "+" + e2.expr;
                        break;
                    case 1:
                        e.answer = e1.answer - e2.answer;
                        e.expr = "(" + e1.expr  + ")-(" + e2.expr + ")";
                        break;
                    case 2:
                        e.answer = e1.answer * e2.answer;
                        e.expr = "(" + e1.expr + ")*(" + e2.expr + ")";
                        break;
                    case 3:
                        e.answer = e1.answer / e2.answer;
                        e.expr = "(" + e1.expr + ")/(" + e2.expr + ")";
                        break;
                }
            }
        }

        if(e.expr != "") {
            for (int i = 0; i < rnd.nextInt(3); ++i) {
                if (rnd.nextBoolean()) {
                    e.expr = "(" + e.expr + ")";
                    if (rnd.nextBoolean()) {
                        e.expr = "-" + e.expr;
                        e.answer = -1.0 * e.answer;
                    }
                }

            }
        }
        return e;

    }



}
