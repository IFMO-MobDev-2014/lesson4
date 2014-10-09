package ru.ifmo.md.lesson4;

/**
 * Created by Яна on 07.10.2014.
 */
public class MyCalculationEngine implements CalculationEngine{
        String s;
        int cur = 0;
        @Override
        public double calculate(String expression) throws CalculationException {
            char[] arg = expression.toCharArray();
            for (int i = 0; i < expression.length(); i++) {
                if (arg[i] == '-')
                    if (i != 0) {
                        if (arg[i - 1] == '$')
                            throw new CalculationException("Wrong expression");
                        if (arg[i - 1] == '*' || arg[i - 1] == '/'
                                || arg[i - 1] == '+' || arg[i - 1] == '-'
                                || arg[i - 1] == '(' || arg[i - 1] == '~')
                            arg[i] = '$';
                    } else {
                        arg[i] = '$';
                    }
            }
            s = String.valueOf(arg);
            cur = 0;
            return sumSub(true, 0);
        }

    double sumSub(boolean is_first, double cur_left) throws CalculationException{
        double left = cur_left;
        if (is_first)
            left = mulDiv(true, 0);
        if (cur < s.length()) {
            char op = s.charAt(cur);
            if (op == '+' || op == '-') {
                cur++;
                double right = mulDiv(true, 0);
                if (op == '+') {
                    return sumSub(false, left + right);
                } else {
                    return sumSub(false, left - right);
                }
            } else if (op == ')'){
                return left;
            } else {
                throw new CalculationException("Wrong expression");
            }
        } else {
            return left;
        }
    }

    double mulDiv(boolean is_first, double cur_left) throws CalculationException {
        double left = cur_left;
        if (is_first)
            left = unMin();
        if (cur < s.length()) {
            char op = s.charAt(cur);
            if (op == '*' || op == '/') {
                cur++;
                double right = unMin();
                if (op == '*') {
                    return mulDiv(false, left * right);
                } else {
                    return mulDiv(false, left / right);
                }
            } else {
                return left;
            }
        } else {
            return left;
        }
    }

    double unMin() throws CalculationException {
        double res;
        if (cur < s.length()) {
            if (s.charAt(cur) == '$') {
                cur++;
                res = -unMin();
            } else {
                res = brackets();
            }
        } else {
            throw new CalculationException("Wrong expression");
        }
        return res;
    }

    double brackets() throws CalculationException {
        double res;
        if (s.charAt(cur) == '(') {
            cur++;
            res = sumSub(true, 0);
            if (cur < s.length()) {
                if (s.charAt(cur) == ')')
                    cur++;
                else
                    throw new CalculationException("Something wrong with brackets");
            } else {
                throw new CalculationException("Wrong expression");
            }
        } else
            res = numb();
        return res;
    }

    double numb() throws CalculationException {
        int pointcnt = 0;
        int l = cur;
        while (cur < s.length() && (Character.isDigit(s.charAt(cur)) || s.charAt(cur) == '.')) {
            /*if (Character.isDigit(s.charAt(cur))) {
                if (pointcnt == 0) {
                    rt *= 10;
                    rt += (double)(s.charAt(cur) - '0');
                } else {
                    tmp =
                }
            } else if (s.charAt(cur) == '.') {
                if (pointcnt != 0) {
                    throw new CalculationException("Too much points in double");
                } else {
                    pointcnt++;
                }
            } else {
                break;
            }*/
            if (s.charAt(cur) == '.') {
                if (pointcnt >= 1) {
                    throw new CalculationException("Too much points in double");
                }
                pointcnt++;
            }
            cur++;
        }
        int r = cur;
        double res;
        try {
            res = Double.parseDouble(s.substring(l, r));
        } catch (NumberFormatException e) {
            throw new CalculationException("Wrong expression");
        }
        return res;
    }
}
