package ru.ifmo.md.lesson4;

import java.util.Stack;

/**
 * Created by ComradeK on 05/10/2014.
 */
public class MyCalculationEngine implements CalculationEngine {
    public double calculate(String expression) throws CalculationException {
        char[] b = new char[expression.length()];
        Stack<Character> ops = new Stack<Character>(), vals = new Stack<Character>();
        int i = 0, j = -1, k = -1;
        while (i < expression.length()) {
            if (!Character.isWhitespace(expression.charAt(i))) {
                j++;
                b[j] = expression.charAt(i);
            }
            i++;
        }
        char[] c = b;
        i = 1;
        if (c[0] == '-')
            c[0] = 'U';
        while (i < j) {
            if ((c[i] == '-') && ((c[i - 1] == '-') || (c[i - 1] == '+') || (c[i - 1] == '*') || (c[i - 1] == '/') || (c[i - 1] == 'U') || (c[i - 1] == '(')))
                c[i] = 'U';
            i++;
        }
        for (i = 0; i <= j; i++) {
            if (Character.isDigit(c[i]) || (c[i] == '.')) {
                vals.push(c[i]);
            } else {
                vals.push(' ');
                if (c[i] == '(') {
                    ops.push('(');
                } else if (c[i] == ')') {
                    char d = ops.pop();
                    while (d != '(') {
                        vals.push(d);
                        d = ops.pop();
                    }
                    while (!ops.isEmpty() && ((ops.peek() == 'U')))
                        vals.push(ops.pop());
                } else {
                    while (!ops.empty() && ((checkPriority(c[i]) <= checkPriority(ops.peek())))) {
                        if ((c[i] != 'U') || ((ops.peek() != 'U') ))
                            vals.push(ops.pop());
                        else
                            break;
                    }
                    ops.push(c[i]);
                }
            }
        }
        while (!ops.empty()) {
            vals.push(ops.pop());
        }
        while (!vals.empty()) {
            ops.push(vals.pop());
        }
        k = -1;
        char[] re = new char[ops.size()];
        while (!ops.empty()) {
            k++;
            re[k] = ops.pop();
        }
        k = 0;
        j = -1;
        String[] rt = new String[re.length];
        while (k < re.length) {
            String m = new String();
            while ((k < re.length) && ((Character.isDigit(re[k]) || (re[k] == '.') ))) {
                m = m + re[k];
                k++;
            }
            if (m.length() > 0) {
                j++;
                rt[j] = m;
            }
            if (k < re.length)
                if (re[k] != ' ') {
                    j++;
                    rt[j] = new String() + re[k];
                }
            k++;
        }
        i = 1;
        while (i <= j) {
            if (rt[i].equals("U") && Character.isDigit(rt[i - 1].charAt(0))) {
                rt[i] = "-" + rt[i - 1];
                rt[i - 1] = "";
            }
            i++;
        }
        double res = 9d;
        Stack<Double> vars = new Stack<Double>();
        for (i = 0; i <= j; i++)
            if (rt[i].length() > 0)
            if (Character.isDigit(rt[i].charAt(0)) || rt[i].length() > 1)
                vars.push(Double.parseDouble(rt[i]));
            else
                {
                    double first = vars.pop();
                    switch (rt[i].charAt(0)) {
                        case '+':
                            vars.push(vars.pop() + first);
                            break;
                        case '-':
                            vars.push(vars.pop() - first);
                            break;
                        case '*':
                            vars.push(vars.pop() * first);
                            break;
                        case '/':
                            if (first == 0d)
                                throw new CalculationException();
                            vars.push(vars.pop() / first);
                            break;
                        case 'U':
                            vars.push(-first);
                            break;
                    }

                }
        if (vars.empty() || vars.size() > 1)
            throw new CalculationException();
        return vars.pop();
}

    static int checkPriority(char a) {
        if (a == '(')
            return -1;
        if ((a == '-') || (a == '+'))
            return 0;
        if ((a == '*') || (a == '/'))
            return 1;
        return 2;
    }
}
