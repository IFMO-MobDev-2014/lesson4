package ru.ifmo.md.lesson4;

/**
 * Created by fotyev on 04-Oct-14.
 */
public class Lexer {
    private String str;
    private int currentOffset = 0;

    public Lexer(String str) {
        this.str = str;
    }

    public char nextToken() {
        nextChar();
        skipWhitespace();
        return peekToken();
    }

    public char nextChar() {
        if (hasMoreTokens()) {
            ++currentOffset;
        }
        return peekToken();
    }

    public void skipWhitespace() {
        while (Character.isWhitespace(peekToken())) {
            nextChar();
        }
    }

    public char peekToken() {
        if (currentOffset >= 0 && currentOffset < str.length()) {
            return str.charAt(currentOffset);
        }
        return '\0';
    }

    public boolean hasMoreTokens() {
        return peekToken() != '\0';
    }

    public int getCurrentIndex() {
        return currentOffset;
    }

    public boolean accept(char tok) {
        if (peekToken() == tok) {
            nextToken();
            return true;
        }
        return false;
    }

}
