package ru.ifmo.md.lesson4;

public class MyCalculateEngine implements CalculationEngine {
    static private Result add(String s) throws CalculationException {
        if (s.isEmpty()) throw new CalculationException();
        Result res = mul(s);
        s = res.rest;
        boolean cont = !s.isEmpty() && (s.charAt(0) == '+' || s.charAt(0) == '-');
        while (cont) {
            char sign = s.charAt(0);
            s = s.substring(1);
            Result next = mul(s);
            s = next.rest;
            double exp;
            if (sign == '+') {
                exp = res.num + next.num;
            } else if (sign == '-') {
                exp = res.num - next.num;
            } else throw new CalculationException();
            cont = !s.isEmpty() && (s.charAt(0) == '+' || s.charAt(0) == '-');
            res = new Result(exp, s);
        }
        return res;
    }

    static private Result mul(String s) throws CalculationException {
        if (s.isEmpty()) throw new CalculationException();
        Result res = br(s);
        s = res.rest;
        boolean cont = !s.isEmpty() && (s.charAt(0) == '*' || s.charAt(0) == '/');
        while (cont) {
            char sign = s.charAt(0);
            s = s.substring(1);
            Result next = br(s);
            s = next.rest;
            double exp;
            if (sign == '*') {
                exp = res.num * next.num;
            } else if (sign == '/') {
                exp = res.num / next.num;
            } else throw new CalculationException();
            cont = !s.isEmpty() && (s.charAt(0) == '*' || s.charAt(0) == '/');
            res = new Result(exp, s);
        }
        return res;
    }

    static private Result br(String s) throws CalculationException {
        if (s.isEmpty()) throw new CalculationException();
        if (s.charAt(0) == '(') {
            s = s.substring(1);
            Result inside = add(s);
            s = inside.rest;
            if (s.isEmpty()) throw new CalculationException();
            if (s.charAt(0) == ')') {
                return new Result(inside.num, s.substring(1));
            } else {
                throw new CalculationException();
            }
        } else if (s.charAt(0) == '-') {
            Result arg = br(s.substring(1));
            return new Result(-arg.num, arg.rest);
        } else if (s.charAt(0) == '+') {
            return br(s.substring(1));
        } else if (s.charAt(0) == '∞') {
            s = s.substring(1);
            return new Result(Double.POSITIVE_INFINITY, s);
        } else {
            int lastDigit = 0;
            while (lastDigit < s.length() && (Character.isDigit(s.charAt(lastDigit)) || s.charAt(lastDigit) == '.'))
                lastDigit++;
            double res;
            try {
                res = Double.parseDouble(s.substring(0, lastDigit));
            } catch (NumberFormatException e) {
                throw new CalculationException();
            }
            s = s.substring(lastDigit);
            return new Result(res, s);
        }
    }

    @Override
    public double calculate(String s) throws CalculationException {
        Result res = add(s);
        if (res.rest.isEmpty()) {
            return res.num;
        } else {
            throw new CalculationException();
        }
    }

    static private class Result {
        public double num;
        public String rest;

        public Result(double num, String rest) {
            this.num = num;
            this.rest = rest;
        }
    }
}
