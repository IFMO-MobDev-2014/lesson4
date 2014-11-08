package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {
    String mes;

    CalculationException(String s, int pos) {
        mes = "at position " + pos + "\n";
        mes += s + "\n";
        for (int i = 0; i < pos; i++) {
            mes += " ";
        }
        mes += "^~~" + "\n";
    }

    public String getMessage() {
        return mes;
    }

    CalculationException(String t) {
        super(t);
    }
}
