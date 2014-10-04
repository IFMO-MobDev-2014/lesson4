package ru.ifmo.md.lesson4;

import java.util.Stack;

/**
 * Created by vi34 on 04.10.14.
 */
public class MyCalculationEngine implements CalculationEngine{
    static Stack<Character> oper = new Stack<Character>();
    static Stack<Double> st = new Stack<Double>();

    @Override
    public double calculate(String expression) throws CalculationException {
        boolean unary = true;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (!Character.isWhitespace(c)) {
                if (c == '(') {
                    oper.push('(');
                    unary = true;
                } else if (c == ')') {
                    while (oper.peek() != '(' && oper.peek() != '|'
                            && oper.peek() != 'L') {
                        execute(oper.peek());
                        oper.pop();
                    }
                    if (oper.peek() == '|' || oper.peek() == 'L') {
                        execute(oper.peek());
                    }
                    oper.pop();
                    unary = false;
                }   else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    if (unary && c == '-') {
                        c = '!';
                    }
                    while (!oper.empty()
                            && (assoc(c)
                            && priority(oper.peek()) >= priority(c) || !assoc(c)
                            && priority(oper.peek()) > priority(c))) {
                        execute(oper.peek());
                        oper.pop();
                    }
                    oper.push(c);
                    unary = true;
                } else {
                        String num = "";
                        int dots = 0;
                        while (i < expression.length() && ((expression.charAt(i) >= '0'
                                && expression.charAt(i) <= '9') || ((dots++ == 0) && expression.charAt(i) == '.'))) {
                            num += Character.toString(expression.charAt(i));
                            ++i;
                        }
                        --i;
                        try {
                            double a =  Double.parseDouble(num);
                            st.push(a);
                        } catch (Exception e)
                        {
                            throw new CalculationException("incorrect expression");
                        }


                    unary = false;
                }
            }
        }
        while (!oper.empty()) {
            execute(oper.peek());
            oper.pop();
        }
        return st.peek();
    }

    private static void execute(char op) {
        if (op != '!') {
            double top1 = st.peek();
            st.pop();
            double top2 = st.peek();
            st.pop();
            switch (op) {
                case '-':
                    st.push(top2 - top1);
                    break;
                case '+':
                    st.push(top2 + top1);
                    break;
                case '*':
                    st.push(top2 * top1);
                    break;
                case '/':
                    st.push(top2 / top1);
                    break;
            }
        } else {
            Double top = st.peek();
            st.pop();
            if (op == '!') {
                st.push(top * -1);
            }
        }
    }

    private static boolean assoc(char op) {
        if (op == '!') {
            return false;
        } else {
            return true;
        }
    }

    private static int priority(char op) {
        if (op == '!') {
            return 4;
        } else {
            return op == '+' || op == '-' ? 1 : op == '*' || op == '/' ? 2 : -1;
        }
    }
}
