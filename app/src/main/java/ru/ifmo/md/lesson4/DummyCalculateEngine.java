package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {

    private final static int SUM_PRIORITY = 1;
    private final static int MUL_PRIORITY = 2;
    private final static char LEFT_BRACKET = '(';
    private final static char RIGHT_BRACKET = ')';
    private final static char MINUS = '-';
    private final static char PLUS = '+';
    private final static char MUL = '*';
    private final static char DIV = '/';
    private final static char UP_CASE_EXP = 'E';
    private final static char DOWN_CASE_EXP = 'e';
    private final static char POINT = '.';
    private final static double EPS = 1e-9;

    @Override
    public double calculate(String s) throws CalculationException {
        /*String s0 = s.replaceAll(" ", "");
        double result = evaluate(s0, 0, s0.length() - 1);
        if (Math.abs(result - (int) Math.round(result)) < EPS) {
            return (int) result;
        } else {
            return result;
        }*/
        try {
            MyDouble tmp = new MyDouble();
            double result = ExpressionParser.parse(s, new MyDouble()).evaluate(tmp.getValue(0), tmp.getValue(0), tmp.getValue(0)).getDouble();
            return result;

        } catch (Throwable e) {
            throw new CalculationException();
        }
    }

    private double evaluate(String s, int l, int r) throws CalculationException {
        if (s.isEmpty())
            return 0.0;
        int k = -1;
        int last_priority = 3;
        int b = 0;

        for (int i = l; i <= r; i++) {
            if (s.charAt(i) == LEFT_BRACKET) b++;
            else if (s.charAt(i) == RIGHT_BRACKET) b--;
            if (b == 0) {
                if ((s.charAt(i) == PLUS || s.charAt(i) == MINUS) && (i == l || (s.charAt(i - 1) != UP_CASE_EXP && s.charAt(i - 1) != DOWN_CASE_EXP)) && last_priority >= SUM_PRIORITY) {
                    k = i;
                    last_priority = SUM_PRIORITY;
                } else if ((s.charAt(i) == MUL || s.charAt(i) == DIV) && last_priority >= MUL_PRIORITY) {
                    k = i;
                    last_priority = MUL_PRIORITY;
                }
            }
        }

        if (!(l >= 0 && l < s.length() && r >= 0 && r < s.length() && l <= r)) {
            throw new CalculationException();
        }

        if (k == -1) {
            if (s.charAt(l) == LEFT_BRACKET && s.charAt(r) == RIGHT_BRACKET) {
                return evaluate(s, l + 1, r - 1);
            } else if ((Character.isDigit(s.charAt(r)) || s.charAt(r) == POINT)) {
                try {
                    String s0 = s.substring(l, r + 1);
                    return Double.parseDouble(s0);
                } catch (Exception e) {
                    throw new CalculationException();
                }
            } else {
                throw new CalculationException();
            }
        } else {
            if (s.charAt(k) == PLUS) {
                if (k == l) {
                    return evaluate(s, k + 1, r);
                } else {
                    return evaluate(s, l, k - 1) + evaluate(s, k + 1, r);
                }
            } else if (s.charAt(k) == MINUS) {
                if (k == l) {
                    return -evaluate(s, k + 1, r);
                } else if (s.charAt(k - 1) == MUL) {
                    return evaluate(s, l, k - 2) * (-evaluate(s, k + 1, r));

                } else if (s.charAt(k - 1) == DIV) {
                    return evaluate(s, l, k - 2) / (-evaluate(s, k + 1, r));


                } else {
                    return evaluate(s, l, k - 1) - evaluate(s, k + 1, r);
                }
            } else if (s.charAt(k) == MUL) {
                return evaluate(s, l, k - 1) * evaluate(s, k + 1, r);
            } else if (s.charAt(k) == DIV) {
                return evaluate(s, l, k - 1) / evaluate(s, k + 1, r);
            }
        }

        throw new CalculationException();
    }
}
