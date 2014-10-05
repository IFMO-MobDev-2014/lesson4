package ru.ifmo.md.lesson4;

import java.util.Stack;

/**
 * Created by vi34 on 04.10.14.
 */
public class MyCalculationEngine implements CalculationEngine{
    Stack<Character> oper = new Stack<Character>();
    Stack<Double> st = new Stack<Double>();

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
                    if(oper.isEmpty())
                    {
                        throw new CalculationException("Missing brackets");
                    }
                    while (oper.peek() != '(') {
                        if(oper.isEmpty())
                        {
                            throw new CalculationException("Missing brackets");
                        }
                        execute(oper.peek());
                        oper.pop();
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
        double result;

        if(st.isEmpty()){
            throw new CalculationException("Missing arguments");
        } else {
            result = st.peek();
        }

        st.pop();
        if(st.isEmpty()) {
            return result;
        } else {
            throw new CalculationException("Missing Operations");
        }
    }

    private void execute(char op) throws CalculationException{
        if (st.isEmpty())
        {
            throw new CalculationException("Missing arguments");
        }
        Double top = st.peek();
        st.pop();
        if (op != '!') {
            if (st.isEmpty())
            {
                throw new CalculationException("Missing arguments");
            }
            double top2 = st.peek();
            st.pop();
            switch (op) {
                case '-':
                    st.push(top2 - top);
                    break;
                case '+':
                    st.push(top2 + top);
                    break;
                case '*':
                    st.push(top2 * top);
                    break;
                case '/':
                    st.push(top2 / top);
                    break;
            }
        } else {
                st.push(top * -1);
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
