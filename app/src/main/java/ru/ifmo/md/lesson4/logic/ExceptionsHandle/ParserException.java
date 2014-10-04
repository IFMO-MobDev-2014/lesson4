package ru.ifmo.md.lesson4.logic.ExceptionsHandle;

import android.util.Log;

/**
 * Created by Сергей on 15.04.14.
 */
public class ParserException extends Exception {

    public ParserException(String message, int position) {
        super("Error");
        Log.e("ParserException", message + " at position " + position);
    }
}
