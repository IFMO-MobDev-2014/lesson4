package ru.ifmo.md.lesson4;

/**
 * Created by Snopi on 02.10.2014.
 */
public class Operations {
    /**
     * Created by User on 02.10.2014.
     */
    public static class Subtract extends BinaryOperation {

        public Subtract(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        double BinaryFunction(double x, double y) {
            return x - y;
        }
    }

    /**
     * Created by User on 02.10.2014.
     */
    public static class Sum extends BinaryOperation {
        public Sum(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        double BinaryFunction(double x, double y) {
            return x + y;
        }
    }

    /**
     * Created by User on 02.10.2014.
     */
    public static class UnaryPlus implements Expr {

        private Expr arg;

        public UnaryPlus(Expr arg) {
            this.arg = arg;
        }
        @Override
        public double evaluate() {
            return arg.evaluate();
        }
    }

    /**
     * Created by User on 02.10.2014.
     */
    public static class Neg implements Expr {

        private Expr arg;

        public Neg(Expr arg) {
            this.arg = arg;
        }

        @Override
        public double evaluate() {
            return -arg.evaluate();
        }
    }

    /**
     * Created by User on 02.10.2014.
     */
    public static class Multiply extends BinaryOperation {
        public Multiply(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        double BinaryFunction(double x, double y) {
            return x * y;
        }
    }

    /**
     * Created by User on 02.10.2014.
     */
    public static class Division extends BinaryOperation {
        public Division(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        double BinaryFunction(double x, double y) {
            return x / y;
        }
    }

    /**
     * Created by User on 02.10.2014.
     */
    public static class Constant implements Expr {
        private double cnst;

        public Constant(double cnst) {
            this.cnst = cnst;
        }

        @Override
        public double evaluate() {
            return cnst;
        }
    }

    /**
     * Created by User on 01.10.2014.
     */
    public abstract static class BinaryOperation implements Expr {
        protected Expr left;
        protected Expr right;

        public BinaryOperation(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double evaluate() {
            return BinaryFunction(left.evaluate(), right.evaluate());
        }

        abstract double BinaryFunction(double x, double y);
    }
}
