package ru.ifmo.md.lesson4.expressionParser;

import java.io.*;
import java.util.*;

public class OverflowException extends ArithmeticException {
    public OverflowException(String message) {
        super(message);    
    }
}