package ru.ifmo.md.lesson4;

import java.util.EmptyStackException;
import java.util.Stack;

public class ExpressionParser implements CalculationEngine {

    private static enum OpType {
        PLUS,
        MINUS,
        MUL,
        DIV,
        UNM,
        UNP,
        NUM,
        OPENBRACE,
        CLOSEBRACE
    }

    ;

    private static boolean isOp(OpType ot) {
        return ot != OpType.OPENBRACE && ot != OpType.CLOSEBRACE && ot != OpType.NUM;
    }

    private static boolean isVarNum(OpType ot) {
        return ot == OpType.NUM;
    }

    private static boolean isVarNumBrace(OpType ot) {
        return isVarNum(ot) || ot == OpType.CLOSEBRACE;
    }

    private static int getPriority(OpType ot) {
        switch (ot) {
            case PLUS:
            case MINUS:
                return 1;
            case MUL:
            case DIV:
                return 2;
            case UNM:
            case UNP:
                return 3;
        }
        return -1;
    }

    private static class BoxedInt {
        private int value;

        public BoxedInt(int value) {
            this.value = value;
        }

        public void add(int other) {
            value += other;
        }

        public int get() {
            return value;
        }
    }

    private static boolean isLeftAssoc(OpType ot) {
        return (ot != OpType.UNM && ot != OpType.UNP);
    }

    private static class Token {

        final OpType type;
        final Double value;

        public Token(OpType type, Double value) {
            this.type = type;
            this.value = value;
        }

        public Token(OpType type) {
            this.type = type;
            this.value = null;
        }
    }

    static private Token nextToken(String input, BoxedInt pos) throws CalculationException {
        String s = input;
        if (input.length() <= pos.get())
            return null;

        Character first = s.charAt(pos.get());

        if ("0123456789.".indexOf(first) != -1) {
            int e = pos.get();
            while (pos.get() < input.length() && "01234567890.".indexOf(s.charAt(pos.get())) != -1) {
                pos.add(1);
            }

            double rv;
            try {
                rv = Double.parseDouble(s.substring(e, pos.get()));
            } catch (NumberFormatException ex) {
                throw new CalculationException("Bad number format", ex);
            }

            return new Token(OpType.NUM, rv);
        }

        pos.add(1);

        switch (first) {
            case '(':
                return new Token(OpType.OPENBRACE);
            case ')':
                return new Token(OpType.CLOSEBRACE);
            case '+':
                return new Token(OpType.PLUS);
            case '-':
                return new Token(OpType.MINUS);
            case '/':
                return new Token(OpType.DIV);
            case '*':
                return new Token(OpType.MUL);
        }

        throw new CalculationException("Invalid token " + first);
    }

    private static void processOp(Stack<Token> stk, Stack<Double> outStk) throws CalculationException {
        try {
            Token op2 = stk.pop();
            Double arg1 = outStk.pop();

            switch (op2.type) {
                case PLUS:
                    outStk.push(outStk.pop() + arg1);
                    break;
                case MINUS:
                    outStk.push(outStk.pop() - arg1);
                    break;
                case MUL:
                    outStk.push(outStk.pop() * arg1);
                    break;
                case DIV:
                    outStk.push(outStk.pop() / arg1);
                    break;
                case UNM:
                    outStk.push(-arg1);
                    break;
                case UNP:
                    outStk.push(arg1);
                default:
            }
        } catch (EmptyStackException ex) {
            throw new CalculationException("Malformed expression", ex);
        }

    }

    private static double shuntingYardAlgo(String input) throws CalculationException {
        Stack<Token> stk = new Stack<Token>();
        Stack<Double> outStk = new Stack<Double>();

        BoxedInt pos = new BoxedInt(0);

        Token prev = null;
        Token cur = nextToken(input, pos);
        if (cur == null)
            return 0.0;

        int braceBalance = 0;

        do {

            if (cur.type == OpType.MINUS && (prev == null || !isVarNumBrace(prev.type))) {
                cur = new Token(OpType.UNM);
            }

            if (cur.type == OpType.PLUS && (prev == null || !isVarNumBrace(prev.type))) {
                cur = new Token(OpType.UNP);
            }

            if (cur.type == OpType.NUM) {
                outStk.push(cur.value);
            } else if (isOp(cur.type)) {
                while (!stk.isEmpty() && isOp(stk.peek().type)
                        && (isLeftAssoc(cur.type) && getPriority(cur.type) <= getPriority(stk.peek().type)
                        || !isLeftAssoc(cur.type) && getPriority(cur.type) < getPriority(stk.peek().type))) {

                    processOp(stk, outStk);
                }

                stk.push(cur);
            } else if (cur.type == OpType.OPENBRACE) {
                braceBalance++;
                if (prev != null && (prev.type == OpType.CLOSEBRACE || prev.type == OpType.NUM))
                    throw new CalculationException("Malformed expression");
                stk.push(cur);
            } else if (cur.type == OpType.CLOSEBRACE) {
                braceBalance--;
                if (prev != null && (prev.type == OpType.OPENBRACE || isOp(prev.type)))
                    throw new CalculationException("Empty braces");
                if (braceBalance < 0)
                    throw new CalculationException("Mismatched braces");
                while (!stk.empty() && stk.peek().type != OpType.OPENBRACE) {
                    processOp(stk, outStk);
                }

                if (stk.empty())
                    throw new CalculationException("Malformed expression");

                stk.pop();
            }
            prev = cur;
            cur = nextToken(input, pos);
        } while (cur != null);

        if (braceBalance != 0)
            throw new CalculationException("Mismatched braces");

        while (!stk.isEmpty()) {
            processOp(stk, outStk);
        }

        if (outStk.empty())
            return 0.0;

        return outStk.pop();
    }

    public static double parse(String a) throws CalculationException {
        return shuntingYardAlgo(a.replaceAll("\\s", ""));
    }

    public double calculate(String s) throws CalculationException {
        return parse(s);
    }
}
