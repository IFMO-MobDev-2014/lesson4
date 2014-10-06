package ru.ifmo.md.lesson4.engine;

import java.util.regex.Pattern;

/**
 * Created by dimatomp on 06.10.14.
 */
/*package local*/ enum Constant {
    E("e", Math.E), PI("pi", Math.PI);
    public static final Pattern constantPattern;

    static {
        StringBuilder builder = new StringBuilder();
        for (Constant constant : values()) {
            builder.append(constant.name);
            builder.append("|");
        }
        builder.deleteCharAt(builder.length() - 1);
        constantPattern = Pattern.compile(builder.toString());
    }

    public final double value;
    private final String name;

    Constant(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public static Double findByName(String name) {
        for (Constant constant : values())
            if (constant.name.equals(name))
                return constant.value;
        return null;
    }
}
