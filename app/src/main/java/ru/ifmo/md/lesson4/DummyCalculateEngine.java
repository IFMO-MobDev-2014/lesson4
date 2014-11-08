package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine{
    int p[];
    int stack[];
    private final static double EPS = 1e-9;

    public boolean isSign(char ch) {
        return (ch == '-') || (ch == '+') || (ch == '*') || (ch == '/');
    }

    public double parse(String exp, int l, int r) throws CalculationException {
        if (r - l <= 0) {
            throw new CalculationException();
        }
        if (exp.charAt(l) == '(' && p[l] == r - 1) {
            return parse(exp, l + 1, r - 1);
        }

        for (int i = r - 1; i > l; i--) {
            if (exp.charAt(i) == ')') {
                i = p[i];
                continue;
            }
            if (exp.charAt(i) == '+' && !isSign(exp.charAt(i - 1))) {
                return parse(exp, l, i) + parse(exp, i + 1, r);
            }
            if (exp.charAt(i) == '-' && !isSign(exp.charAt(i - 1))) {
                return parse(exp, l, i) - parse(exp, i + 1, r);
            }
        }
        for (int i = r - 1; i > l; i--) {
            if (exp.charAt(i) == ')') {
                i = p[i];
                continue;
            }
            if (exp.charAt(i) == '*') {
                return parse(exp, l, i) * parse(exp, i + 1, r);
            }
            if (exp.charAt(i) == '/') {
                double denominator = parse(exp, i + 1, r);
                if (Math.abs(denominator) < EPS) {
                    throw new CalculationException();
                }
                return parse(exp, l, i) / denominator;
            }
        }
        if (exp.charAt(l) == '+')
            return parse(exp, l + 1, r);
        if (exp.charAt(l) == '-')
            return -parse(exp, l + 1, r);
        boolean onlyDigits = true;
        int cntDot = 0;
        for (int i = l; i < r; i++) {
            onlyDigits &= Character.isDigit(exp.charAt(i)) || exp.charAt(i) == '.';
            if (exp.charAt(i) == '.')
                cntDot++;
        }
        if (!onlyDigits || cntDot > 1) {
            throw new CalculationException();
        }
        return Double.parseDouble(exp.substring(l, r));
    }

    public double calculate(String exp) throws CalculationException {
        exp = exp.replaceAll("\\s+", "");
        int n = exp.length();
        p = new int[n];
        stack = new int[n];
        int cur = 0;
        for (int i = 0; i < n; i++) {
            if (exp.charAt(i) == '(') {
                stack[cur++] = i;
            }
            if (exp.charAt(i) == ')') {
                if (cur == 0) {
                    throw new CalculationException();
                }
                p[i] = stack[cur - 1];
                p[stack[cur - 1]] = i;
                cur--;
            }
        }
        if (cur != 0) {
            throw new CalculationException();
        }
        return parse(exp, 0, n);
    }
    /*public static void main(String[] args) {
        DummyCalculateEngine t = new DummyCalculateEngine();
        String s = "(-(-(4+-2)/(4-2*2)))";
        try {
            System.err.println(t.calculate(s));
        } catch (CalculationException e) {
            e.printStackTrace();
        }

    }*/
}

