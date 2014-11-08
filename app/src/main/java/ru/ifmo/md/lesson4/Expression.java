package ru.ifmo.md.lesson4;

/**
 * Created by Юлия on 06.10.2014.
 */
public class Expression {

    private String st = "";

    void add(String o) {
        st += o;
    }

    String getText() {
        return st;
    }

    void toEmpty() {
        st = "";
    }

    void poll() {
        if (!st.isEmpty())
            st = st.substring(0, st.length() - 1);
    }
}
