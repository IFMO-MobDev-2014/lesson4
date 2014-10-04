package ru.ifmo.md.lesson4;

/**
 * Created by Snopi on 01.10.2014.
 */
public class ExpressionParser {
    private static String expression;
    private static int i;

    private static Expr parseExpr(Expr left) throws CalculationException {
        i++;
        if (i >= expression.length()) {
            return left;
        }
        char c;
        c = expression.charAt(i);
        if (c != '+' && c != '-') {
            i--;
            return left;
        }
        Expr right = parseTerm(parseAtom());
        if (c == '-') {
            return parseExpr(new Operations.Subtract(left, right));
        } else if (c == '+') {
            return parseExpr(new Operations.Sum(left, right));
        }
        throw new CalculationException();
    }

    private static Expr parseTerm(Expr left) throws CalculationException {
        i++;
        if (i >= expression.length()) {
            return left;
        }
        char c;
        c = expression.charAt(i);
        if (c != '*' && c != '/') {
            i--;
            return left;
        }
        Expr right = parseAtom();
        if (c == '*') {
            return parseTerm(new Operations.Multiply(left, right));
        } else if (c == '/') {
            return parseTerm(new Operations.Division(left, right));
        }
        throw new CalculationException();
    }

    private static Expr parseAtom() throws CalculationException {
        i++;
        if (i >= expression.length()) {
            throw new CalculationException("...");
        }
        char c = expression.charAt(i);
        if (Character.isDigit(c) || c == '.') {
            int dots = 0;
            int indBegin = i;
            while (i < expression.length()
                    && (Character.isDigit(expression.charAt(i))
                    || expression.charAt(i) == '.' || expression.charAt(i) == 'E')) {
                if (expression.charAt(i) == '.') {
                    dots++;
                    if (dots > 1) {
                        throw new CalculationException("too many dots in double");
                    }
                } else if (expression.substring(i).startsWith("E-")) {
                    i++;
                }
                i++;
            }
            double ans;
            try {
                ans = Double.parseDouble(expression.substring(indBegin, i));
            } catch (NumberFormatException e) {
                throw new CalculationException(e.toString());
            }
            i--;
            return new Operations.Constant(ans);
        } else if (c == '(') {
            Expr result = parseExpr(parseTerm(parseAtom()));
            i++;
            if (i >= expression.length() || expression.charAt(i) != ')') {
                throw new CalculationException("Mismatched parentheses");
            }
            return result;
        } else if (c == '-') {
            return new Operations.Neg(parseAtom());
        } else if (c == '+') {
            return new Operations.UnaryPlus(parseAtom());
        }
        throw new CalculationException();
    }

    public static Expr parse(String token) throws CalculationException {
        i = -1;
        expression = token.replaceAll("\\p{javaWhitespace}", "");
        if (token.isEmpty()) {
            throw new CalculationException("There is nothing to calculate");
        }
        Expr result = parseExpr(parseTerm(parseAtom()));
        if (i < expression.length()) {
            throw new CalculationException("Bad expression");
        }
        return result;
    }
}
