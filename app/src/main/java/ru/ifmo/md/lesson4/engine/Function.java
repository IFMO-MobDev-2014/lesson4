package ru.ifmo.md.lesson4.engine;

import java.util.regex.Pattern;

/**
 * Created by dimatomp on 06.10.14.
 */
/*package local*/ enum Function {
    ABS("abs") {
        @Override
        public double calculate(double arg) {
            return Math.abs(arg);
        }
    },
    SIN("sin") {
        @Override
        public double calculate(double arg) {
            return Math.sin(arg);
        }
    },
    COS("cos") {
        @Override
        public double calculate(double arg) {
            return Math.cos(arg);
        }
    },
    LN("ln") {
        @Override
        public double calculate(double arg) {
            return Math.log(arg);
        }
    },
    ASIN("asin") {
        @Override
        public double calculate(double arg) {
            return Math.asin(arg);
        }
    },
    ACOS("acos") {
        @Override
        public double calculate(double arg) {
            return Math.acos(arg);
        }
    },
    TAN("tan") {
        @Override
        public double calculate(double arg) {
            return Math.tan(arg);
        }
    },
    ATAN("atan") {
        @Override
        public double calculate(double arg) {
            return Math.atan(arg);
        }
    },
    SQRT("sqrt") {
        @Override
        public double calculate(double arg) {
            return Math.sqrt(arg);
        }
    },
    CBRT("cbrt") {
        @Override
        public double calculate(double arg) {
            return Math.cbrt(arg);
        }
    };
    public static final Pattern functionPattern;

    static {
        StringBuilder builder = new StringBuilder();
        for (Function function : values()) {
            builder.append(function.name);
            builder.append("|");
        }
        builder.deleteCharAt(builder.length() - 1);
        functionPattern = Pattern.compile(builder.toString());
    }

    private final String name;

    Function(String name) {
        this.name = name;
    }

    public static Function findByName(String name) {
        for (Function function : values())
            if (function.name.equals(name))
                return function;
        return null;
    }

    public abstract double calculate(double arg);
}
