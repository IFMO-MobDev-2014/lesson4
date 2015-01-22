package ru.ifmo.md.lesson4.expressionParser;

import java.io.*;
import java.util.*;

public class ExpressionParseError extends ArithmeticException {
    public ExpressionParseError(String message) {
        super(message);
    }
}