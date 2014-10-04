package ru.ifmo.md.lesson4;

import android.util.Pair;

import java.util.Random;

/**
 * Created by Snopi on 04.10.2014.
 */
public class TestGen {
    Random random = new Random(10382394l);

    public Pair<Double, String> nextTest() {
        int depth = random.nextInt(11);
        Pair<Pair<Double, String>, Integer> x = recursionGeneration(depth);
        return x.first;
    }

    //+-*/(unary +)(unary -)
    // 012345-
    // 0011223
    private double randomDouble() {
        return (random.nextInt() + random.nextDouble())
                * (random.nextBoolean() ? -1 : 1);
    }

    private Pair<Pair<Double, String>, Integer> recursionGeneration(int depth) {
        double ans;
        if (depth == 0) {
            ans = randomDouble();
            return tripleGen(ans, "" + ans, 3);
        }
        depth -= 1;
        int operation = random.nextInt(6);
        if (operation <= 3) {
            Pair<Pair<Double, String>, Integer> left;
            Pair<Pair<Double, String>, Integer> right;
            left = recursionGeneration(depth);
            right = recursionGeneration(depth);
            int lPriority = left.second;
            int rPriority = right.second;
            String lRes = left.first.second;
            String rRes = right.first.second;
            if (operation == 0) {
                if (lPriority == 0) {
                    lRes = "(" + lRes + ")";
                }
                if (rPriority == 0) {
                    rRes = "(" + rRes + ")";
                }
                return tripleGen(left.first.first + right.first.first, lRes + "+" + rRes, 0);
            } else if (operation == 1) {
                if (lPriority == 0) {
                    lRes = "(" + lRes + ")";
                }
                if (rPriority == 0) {
                    rRes = "(" + rRes + ")";
                }
                return tripleGen(left.first.first - right.first.first, lRes + "-" + rRes, 0);
            } else if (operation == 2) {
                if (lPriority <= 1) {
                    lRes = "(" + lRes + ")";
                }
                if (rPriority <= 1) {
                    rRes = "(" + rRes + ")";
                }
                return tripleGen(left.first.first * right.first.first, lRes + "*" + rRes, 1);
            } else {
                if (lPriority <= 1) {
                    lRes = "(" + lRes + ")";
                }
                if (rPriority <= 1) {
                    rRes = "(" + rRes + ")";
                }
                return tripleGen(left.first.first / right.first.first, lRes + "/" + rRes, 1);
            }
        } else {
            Pair<Pair<Double, String>, Integer> operand;
            operand = recursionGeneration(depth);
            int priority = operand.second;
            if (operation == 4) {
                if (priority < 2) {
                    return tripleGen(-operand.first.first, "-(" + operand.first.second + ")", 2);
                } else {
                    return tripleGen(-operand.first.first, "-" + operand.first.second, 2);
                }
            }
            return operand;
        }
    }

    private Pair<Pair<Double, String>, Integer> tripleGen(Double dRes, String sRes, Integer priority) {
        return new Pair<Pair<Double, String>, Integer>(
                new Pair<Double, String>(dRes, sRes), priority);
    }
}
