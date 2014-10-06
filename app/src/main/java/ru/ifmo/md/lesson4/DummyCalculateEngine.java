package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {

    static Cur curnext = new Cur();
    static Cur curprev = new Cur();
    static String s = "";
    static int size;
    static int siz = 0;

    @Override
    public double calculate(String expression) throws CalculationException {
        s = expression;
        curnext = new Cur();
        curprev = new Cur();
        siz = 0;
        size = expression.length();
        if (s.isEmpty()) throw new CalculationException();
        next();
        return expr().evaluate();
    }

    public static void next()  throws CalculationException {
        String s1 = "";
        while (siz < size && Character.isWhitespace(s.charAt(siz)))
            siz++;
        if (siz >= size) {
            curnext = null;
            return;
        }
        switch (s.charAt(siz)) {
            case '+':
            case '*':
            case '/':
            case '(':
            case ')':
                curnext.posit = s.charAt(siz);
                siz++;
                return;
        }
        if (s.charAt(siz) == '-') {
            if (siz + 1 < size && Character.isDigit(s.charAt(siz + 1)) && !prev()) {
                s1 = "-";
                siz++;
            } else {
                curnext.posit = '-';
                siz++;
                return;
            }
        }
        if (Character.isDigit(s.charAt(siz))) {
            boolean fl = false;
            while (siz < size && (Character.isDigit(s.charAt(siz)) || s.charAt(siz) == '.')) {
                if (s.charAt(siz) == '.' && fl) throw new CalculationException();
                if (s.charAt(siz) == '.') fl = true;
                s1 += s.charAt(siz);
                siz++;
            }
            curnext.numb = Double.parseDouble(s1);
            curnext.posit = 'N';
            return;
        }
        throw new CalculationException();
    }

    private static Expression3 expr() throws CalculationException {
        Expression3 a = item();
        if (curnext != null) {
            while (curnext != null && (curnext.posit == '+' || curnext.posit == '-')) {
                if (curnext != null && curnext.posit == '+') {
                    curprev = curnext;
                    next();
                    if (!statement()) throw new CalculationException();
                    a = new Add(a, item());
                }
                if (!prev()) {
                    if (curnext != null && curnext.posit == '-') {
                        curprev = curnext;
                        next();
                        if (!statement()) throw new CalculationException();
                        a = new Subtract(a, item());
                    }
                }
            }
        }
        return a;
    }

    private static Expression3 item() throws CalculationException {
        Expression3 a = mult();
        if (curnext != null) {
            while (curnext != null
                    && (curnext.posit == '*' || curnext.posit == '/')) {
                if (curnext != null && curnext.posit == '*') {
                    curprev = curnext;
                    next();
                    if (!statement()) throw new CalculationException();
                    a = new Multiply(a, mult());
                }
                if (curnext != null && curnext.posit == '/') {
                    curprev = curnext;
                    next();
                    if (!statement()) throw new CalculationException();
                    a = new Divide(a, mult());
                }
            }
        }
        return a;
    }

    private static Expression3 mult() throws CalculationException {
        char s2;
        switch (curnext.posit) {
            case 'N':
                double i = curnext.numb;
                curprev = curnext;
                next();
                if (curnext != null && (curnext.posit == 'N' || curnext.posit == '(')) throw new CalculationException();
                return new Const(i);
            case '(':
                curprev = curnext;
                next();
                if (!statement()) throw new CalculationException();
                Expression3 ex = expr();
                if (curnext == null || !(curnext.posit == ')')) throw new CalculationException();
                next();
                return ex;
            case ')':
                //curprev = curnext;
                //next();
                //break;
                throw new CalculationException();
            case '-':
                s2 = curnext.posit;
                curprev = curnext;
                next();
                if (curnext == null) throw new CalculationException();
                switch(curnext.posit) {
                    case ')':
                    case '+':
                    case '*':
                    case '/':
                        throw new CalculationException();
                }
                return new UnaryMinus(mult());
            default:
                throw new CalculationException();
        }
        //return null;
    }

    private static boolean statement() {
        if (curnext == null) return false;
        switch (curnext.posit) {
            case '+':
            case '*':
            case '/':
                return false;
            default:
                return true;
        }
    }

    private static boolean prev() {
        if (curprev == null)
            return false;
        switch (curprev.posit) {
            case '(':
            case '+':
            case '/':
            case '-':
            case '*':
                return false;
            default:
                return true;
        }
    }

}
