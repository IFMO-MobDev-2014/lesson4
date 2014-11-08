package ru.ifmo.md.lesson4;

import android.util.Log;

import java.util.Stack;

/**
 * Created by Artur on 06.10.2014.
 */
public class MyCalculateEngine implements CalculationEngine  {
    @Override
    public double calculate(String expression) throws CalculationException {
        if (expression.length() == 0) {
            return 0.0;
        }
        char c;
        Stack st = new Stack();
        String pol = new String();
        int sk = 0;
        for (int i = 0; i < expression.length(); i++) {
            c = expression.charAt(i);
            if (c == '(') {
                sk = sk + 1;
            } else if (c == ')') {
                sk = sk - 1;
            }
            if (sk < 0) {
                throw new CalculationException();
            }
        }
        if (sk != 0) {
            throw new CalculationException();
        }
        for (int i = 0; i < expression.length(); i++) {
            c = expression.charAt(i);
            if ('0' <= c && c <= '9' && (i == 0 || ((expression.charAt(i - 1) > '9' || expression.charAt(i - 1) < '0') && expression.charAt(i - 1) != '.'))) {
                if (pol.length() < 2 || pol.charAt(pol.length() - 2) != '^' || pol.charAt(pol.length() - 1) != '-') {
                    pol = pol + '^';
                }
            }
            if ('0' <= c && c <= '9') {
                pol = pol + c;
            } else if (c == '.') {
                pol = pol + c;
            } else if (c == '-' && (i == 0 || expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == '-' || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/' || expression.charAt(i - 1) == ')') && (i < expression.length() - 1 && expression.charAt(i + 1) <= '9' && expression.charAt(i + 1) >= '0')) {
                pol = pol + '^' + c;
            } else if (c == '+' || c == '-') {
                if (st.empty()) {
                    st.push(c);
                } else {
                    while (!st.empty() && !st.peek().equals('(')) {
                        pol = pol + st.peek();
                        st.pop();
                    }
                    st.push(c);
                }

            } else if (c == '*' || c == '/') {
                if (st.empty()) {
                    st.push(c);
                } else {
                    while (!st.empty() && (st.peek().equals('*') || st.peek().equals('/'))) {
                        pol = pol + st.pop();
                    }
                    st.push(c);
                }
            } else if (c == '(') {
                st.push(c);
            } else if (c == ')') {
                while (!st.peek().equals('(')) {
                    pol = pol + st.pop();
                }
                st.pop();
            } else {
                throw new CalculationException();
            }
        }
        Log.i("OPA", "" + pol);
        while (!st.empty()) {
            pol = pol + st.pop();
        }
        Stack<Double> num = new Stack<Double>();
        double t;
        double pow = 1;
        boolean f = false;
        boolean z = false;
        try {
            for (int i = 0; i < pol.length(); i++) {
                c = pol.charAt(i);
                if (c == '-' && pol.charAt(i - 1) == '^') {
                    z = true;
                }
                else if ('0' <= c && c <= '9') {
                    if (!f) {
                        if (z) {
                            num.push(num.pop() * 10 - Character.getNumericValue(c));
                        } else {
                            num.push(num.pop() * 10 + Character.getNumericValue(c));
                        }
                    } else {
                        if (z) {
                            num.push(num.pop() - Character.getNumericValue(c) * pow);
                        } else {
                            num.push(num.pop() + Character.getNumericValue(c) * pow);
                        }
                        pow = pow / 10;
                    }
                } else if (c == '.') {
                    f = true;
                    pow = 0.1;
                } else if (c == '+') {
                    num.push(num.pop() + num.pop());
                } else if (c == '-') {
                    num.push(-num.pop() + num.pop());
                } else if (c == '*') {
                    num.push(num.pop() * num.pop());
                } else if (c == '/') {
                    t = num.pop();
                    num.push(num.pop() / t);
                } else if (c == '^') {
                    f = false;
                    z = false;
                    num.push(0.0);
                }
            }
        } catch (Exception e) {
            throw new CalculationException();
        }
        if (num.empty()) {
            throw new CalculationException();
        }
        t = num.pop();
        if (!num.empty()) {
            throw new CalculationException();
        }
        return t;
    }
}
