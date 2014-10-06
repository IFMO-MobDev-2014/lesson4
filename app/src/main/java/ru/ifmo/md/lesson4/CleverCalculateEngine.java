package ru.ifmo.md.lesson4;

public class CleverCalculateEngine implements CalculationEngine {
    class Answer {
        public double number;
        public String str;

        public Answer(double v, String r) {
            number = v;
            str = r;
        }
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        if (expression.isEmpty()) {
            return 0d;
        }

        Answer result = plusMinus(expression);

        if (result == null) {
            throw new CalculationException("Error: expression didn't correct");
        }
        if (!result.str.isEmpty()) {
            throw new CalculationException("Error: can't full parse");
        }

        return result.number;
    }


    private Answer plusMinus(String s) throws CalculationException {
        if (s.isEmpty())
            throw new CalculationException("Error: incorrect string");
        Answer current = mulDiv(s);
        double num = current.number;
        while (current.str.length() > 0) {
            if (!(current.str.charAt(0) == '+' || current.str.charAt(0) == '-')) break;

            char sign = current.str.charAt(0);
            String next = current.str.substring(1);

            current = mulDiv(next);
            if (sign == '+') {
                num += current.number;
            } else {
                num -= current.number;
            }
        }
        return new Answer(num, current.str);
    }

    private Answer bracket(String s) throws CalculationException {
        if (s.isEmpty())
            throw new CalculationException("Error: incorrect string");
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Answer r = plusMinus(s.substring(1));
            if (!r.str.isEmpty() && r.str.charAt(0) == ')') {
                r.str = r.str.substring(1);
            } else {
                throw new CalculationException("Error: not close bracket");
            }
            return r;
        }
        return number(s);
    }

    private Answer mulDiv(String s) throws CalculationException {
        if (s.isEmpty())
            throw new CalculationException("Error: incorrect string");
        Answer current = bracket(s);
        double num = current.number;
        while (true) {
            if (current.str.length() == 0) {
                return current;
            }

            char sign = current.str.charAt(0);

            if (!('*' == sign || sign == '/')) {
                return current;
            }

            String next = current.str.substring(1);
            Answer right = bracket(next);

            if (sign == '*') {
                num *= right.number;
            } else {
                num /= right.number;
            }

            current = new Answer(num, right.str);
        }
    }

    private Answer number(String s) throws CalculationException {
        if (s.isEmpty())
            throw new CalculationException("Error: incorrect string");
        int i = 0;
        int dotCnt = 0;
        boolean negative = false;

        if (s.charAt(0) == '+') {
            if (s.charAt(1) == '(') {
                Answer ans = plusMinus(s.substring(1));
                return new Answer(ans.number, ans.str);
            } else {
                s = s.substring(1);
            }
        }

        if (s.charAt(0) == '-') {
            if (s.charAt(1) == '(') {
                Answer ans = plusMinus(s.substring(1));
                return new Answer(-ans.number,ans.str);
            } else {
                negative = true;
                s = s.substring(1);
            }
        }

        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            if (s.charAt(i) == '.' && ++dotCnt > 1) {
                throw new CalculationException("not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if( i == 0 ) {
            throw new CalculationException( "can't get valid number in '" + s + "'" );
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if (negative) {
            dPart = -dPart;
        }

        String restPart = s.substring(i);

        return new Answer(dPart, restPart);
    }
}