package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest extends TestCase {
    final double EPS = 1e-6;

    public void expectEq(String expr, double okRes) {
        try {
            double my = CalculationEngineFactory.defaultEngine().calculate(expr);
            if (Math.abs(my - okRes) > EPS)
                Assert.fail(expr + " = " + my + " but correct = " + okRes);
        } catch (CalculationException e) {
            Assert.fail("CalculationException when calculating " + expr);
        }
    }

    public void expectFail(String expr) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expr);
        } catch (CalculationException e) {
            return;
        }
        Assert.fail(expr + " doesn't fail, but failed!");
    }

    public void expectNotFail(String expr) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expr);
        } catch (CalculationException e) {
            Assert.fail(expr + " failed!");
        }
    }

    public void testParser() {
        expectFail("");
        expectFail("(");
        expectFail(")");
        expectFail("((((+");
        expectFail("-----");
        expectFail("-");
        expectFail("111..");
        expectFail(".");
        expectFail("..");
        expectFail("++");
        expectFail("+");
        expectFail("(1+2+3+-4");
        expectFail(")(1+2+3)");
        expectFail("20**30");
        expectFail("40.0//1");
        expectFail("11111111111.../3");
        expectFail("gradle is shit");
        expectFail("best test");
        expectFail("43kdjfdejwkdjlsdl");
        expectFail("((())");
        expectFail("120    +               392a");
        expectFail("a+b + c = d");
        expectFail("----1000//191910");
        expectFail("10+-*10");
        expectFail("(4390)+(3438)/190124.0+((322+1001)*(43.3+200.43))+(13*20+103.32/21");
        expectFail("****");
        expectFail("*");
        expectFail("/");
        expectFail("-");
        expectFail("203003203230034903493.1010..200");
        expectFail("(10)(20)(3)");
        expectFail("9-");
        expectFail("91901.3292+");
        expectFail("9342903.43034+****");
        expectFail("*10*30-4");
        expectFail("(*-.)");
        expectFail("1-3(4)");

        expectNotFail("439043+++++--++--++-30+8");
        expectNotFail("20");
        expectNotFail("30+10");
        expectNotFail("1.");
        expectNotFail("1.+3.*4.1");
        expectNotFail(".5");
        expectNotFail("(310101+39290-1.001*0.5)");
        expectNotFail("20+111-2901.392/1920.4+3920*29*19*33");
        expectNotFail("0003+10");
        expectNotFail("(13+20-30*2.40)/(10+20-1)*3");
        expectNotFail("(10+392*(32.9020-392.439-20*.5+10*(12+12)-((13+20-30*2.40)/(10+20-1)*3*-5*10+(1.2   + 2.3 + 3020)))-30/2)");
        expectNotFail("(10+392*(32.9020-392.439-20*.5+10*(12+12)-((13+20-30*2.40)/(10+20-1)*3*-5*10+(1.2   + 2.3 + 3020)))-30/2)+39.2 * (-3) * (---5) + 0.0 + 32+-1");
        expectNotFail("-1-2-3+4");
        expectNotFail("555555");
        expectNotFail("(2392)*(292)+(8392)-(-----5)");
}

    public void testInfinityAndDivisionByZero() {

    }

    public void testCorrectArithmetic() {
        expectEq("-5", -5);
        expectEq("5", 5);
        expectEq("-0.5555", -0.5555);
        expectEq("+0.1", 0.1);
        expectEq("111+++-1", 110);
        expectEq("20*20+3-2*6", 391);
        expectEq("(20+30)*10-10", 490);
    }
}
