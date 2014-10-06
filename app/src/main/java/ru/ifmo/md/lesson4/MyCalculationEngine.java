package ru.ifmo.md.lesson4;

/**
 * Created by vitalik on 06.10.14.
 */
public class MyCalculationEngine implements CalculationEngine {
    String s;
    int bal = 0;
    int last;

    void deleteSpaces() {
        while (last < s.length() && (s.charAt(last) == ' ' || s.charAt(last) == '\t')) {
            last++;
        }
    }

    double operand() throws CalculationException {
        deleteSpaces();
        double res;
        if (last >= s.length())
            throw new CalculationException("unexpected end of epression. \nExpected : operand");
        if (s.charAt(last) == '-') {
            last++;
            char c = s.charAt(last);
            if (c >= '0' && c <= '9') {
                int tmp = last;
                while (last < s.length() && (s.charAt(last) >= '0' && s.charAt(last) <= '9' || s.charAt(last) == '.'))
                last++;
                res = Double.parseDouble(s.substring(tmp, last));
            }
        }
        char c = s.charAt(last);
        if (c >= '0' && c <= '9' || c == '.') {
            int tmp = last;
            while (last < s.length() && (s.charAt(last) >= '0' && s.charAt(last) <= '9' || s.charAt(last) == '.'))
            last++;
            res = Double.parseDouble(s.substring(tmp, last));
        } else
            throw new CalculationException("not correct expression.\nExpected : operand \nFound : " + s.charAt(last));
        return res;
    }

    double brackets() throws CalculationException {
        double res;
        deleteSpaces();
        if (last < s.length() && s.charAt(last) == '(') {
            last++;
            bal++;
            res = expr();
            deleteSpaces();
            if (s.length() == last || s.charAt(last) != ')')
                throw new CalculationException("Missed right bracket");
            else bal--;
            last++;
        } else {
            res = operand();
        }
        return res;
    }

    double factor() throws CalculationException {
        double res = brackets();
        deleteSpaces();
        while (last < s.length()) {
            switch (s.charAt(last)) {
                case '*':
                    last++;
                    res = res * brackets();
                    break;
                case '/':
                    last++;
                    res = res / brackets();
                    break;
//                case 'm':
//                    if (s.length() <= last + 3 || s.charAt(last + 1) != 'o' || s.charAt(last + 2) != 'd')
//                        throw new CalculationException("Not correct expression. Expected: mod");
//                    last += 3;
//                    res = new Mod(res, brackets());
//                    break;
                default:
                    deleteSpaces();
                    if (s.charAt(last) != '+' && s.charAt(last) != '-' && s.charAt(last) != ')')
                        throw new CalculationException("Not correct expression. \nExpected: +, - or ')'.\nFound: " + s.charAt(last));
                    return res;
            }
            deleteSpaces();
        }
        return res;
    }

    double expr() throws CalculationException {
        double res = factor();
        deleteSpaces();
        while (last < s.length()) {
            switch (s.charAt(last)) {
                case '+':
                    last++;
                    res = res + factor();
                    break;
                case '-':
                    last++;
                    res = res - factor();
                    break;
                default:
                    return res;
            }
            deleteSpaces();
        }
        return res;
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        double res = 0;
        s = expression;
        last = 0;
        res = expr();
        while (last < s.length()) {
            if (s.charAt(last) == '(')
                bal++;
            if (s.charAt(last) == ')')
                bal--;
            last++;
        }
        if (bal != 0)
            throw new CalculationException("wrong brackets sequence.");
        return res;
    }
}
