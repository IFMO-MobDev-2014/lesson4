package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.parser.exceptions.ParseException;

class Lexer {
    private final String s;
    private int i, last;

    Lexer(String s) {
        this.s = s;
        i = 0;
    }

    int getLast() {
        return last;
    }

    String parseNumber() {
        int j = i;
        while (j + 1 < s.length() && Character.isDigit(s.charAt(j + 1))) {
            j++;
        }
        if (j + 1 < s.length() && s.charAt(j + 1) == '.') {
            j++;
            while (j + 1 < s.length() && Character.isDigit(s.charAt(j + 1))) {
                j++;
            }
        }
        if (j + 1 < s.length() && (s.charAt(j + 1) == 'e' || s.charAt(j + 1) == 'E')) {
            j++;
            if (j + 1 < s.length() && s.charAt(j + 1) == '-') {
                j++;
            }
            while (j + 1 < s.length() && Character.isDigit(s.charAt(j + 1))) {
                j++;
            }
        }
        String res = s.substring(i, j + 1);
        i = j + 1;
        return res;
    }

    String next() throws ParseException {
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        last = i;
        if (i == s.length()) {
            return "";
        }
        if (Character.isDigit(s.charAt(i))) {
            return parseNumber();
        }
        if (Character.isLetter(s.charAt(i))) {
            int j = i;
            while (j + 1 < s.length() && Character.isLetter(s.charAt(j + 1))) {
                j++;
            }
            int oi = i;
            i = j + 1;
            String res = s.substring(oi, j + 1);
            return res;
        }
        i++;
        return s.substring(i - 1, i);
    }
}
