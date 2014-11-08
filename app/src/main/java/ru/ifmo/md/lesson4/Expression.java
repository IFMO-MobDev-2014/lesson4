package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public interface Expression {
    public BigDecimal evaluate() throws Exception;
    public String toString();
    public int getPriority();
}
