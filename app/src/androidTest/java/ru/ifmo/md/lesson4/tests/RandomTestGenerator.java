package ru.ifmo.md.lesson4.tests;

import java.util.Random;

/**
 * Created by anton on 02/10/14.
 */
public class RandomTestGenerator {

    public static class TestCase {
        public final double result;
        public final String expression;

        public TestCase(double result, String expression) {
            this.result = result;
            this.expression = expression;
        }
    }

    Random rng;

    RandomTestGenerator(Random rng) {
        this.rng = rng;
    }

    public TestCase generateTerm(int type, TestCase... children) {
        if (type == 0) {
            double term = rng.nextDouble();
            return new TestCase(term, Double.toString(term));
        } else if (type == 1) {
            return new TestCase(-children[0].result, "-(" + children[0].expression + ")");
        } else if (type == 2) {
            return new TestCase(children[0].result, "+(" + children[0].expression + ")");
        } else if (type == 3) {
            double result = children[0].result + children[1].result;
            String expression = "(" + children[0].expression + ") + (" + children[1].expression + ")";
            return new TestCase(result, expression);
        } else if (type == 4) {
            double result = children[0].result - children[1].result;
            String expression = "(" + children[0].expression + ") - (" + children[1].expression + ")";
            return new TestCase(result, expression);
        } else if (type == 5) {
            double result = children[0].result * children[1].result;
            String expression = "(" + children[0].expression + ") * (" + children[1].expression + ")";
            return new TestCase(result, expression);
        } else /*if (type == 6)*/ {
            double result = children[0].result / children[1].result;
            String expression = "(" + children[0].expression + ") / (" + children[1].expression + ")";
            return new TestCase(result, expression);
        }
    }

    public TestCase generateExpression(int depth) {
        if (depth == 0) {
            return generateTerm(0);
        } else {
            int type = rng.nextInt(6) + 1;
            if (type == 1 || type == 2) {
                return generateTerm(type, generateExpression(depth - 1));
            } else {
                return generateTerm(type, generateExpression(depth - 1), generateExpression(depth - 1));
            }
        }
    }
}
