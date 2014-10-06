package ru.ifmo.md.lesson4;

public class MyCalculateEngine implements CalculationEngine {

    public class MegaParser {
        public String s;
        public char[] actions = {'+', '-', '*', '/'};
        public int getBalance(int l, int r) {
            int balance = 0;
            for(int i = l; i <= r; i++) {
                balance = balance + (s.charAt(i) == '(' ?1:0);
                balance = balance - (s.charAt(i) == ')'?1:0);
                if (balance < 0) {
                    return -1;
                }
            }
            return balance;
        }
        public int pos(char c) {
            for(int i = 0; i < 4; i++){
                if (actions[i] == c) {
                    return i;
                }
            }
            return -1;
        }
        public double parse(int l, int r) throws CalculationException {
            System.out.println("Pos: "+l+" "+r);
            if (l > r) {
                return 0;
            } else {
                if (Character.isWhitespace(s.charAt(l))) {
                    return parse(l + 1,  r);
                }
                if (Character.isWhitespace(s.charAt(r))) {
                    return parse(l,  r - 1);
                }
                if (s.charAt(l) == '(' && s.charAt(r) == ')' && getBalance(l + 1, r - 1) == 0) {
                    return parse(l + 1,  r - 1);
                }

                for(int q = 0; q < actions.length; q++) {
                    int balance = 0;
                    for(int i = l; i <= r; i++) {
                        balance = balance + (s.charAt(i) == '(' ?1:0);
                        balance = balance - (s.charAt(i) == ')'?1:0);
                        if (balance == 0 && s.charAt(i) == actions[q]) {
                            if (actions[q] == '+') {
                                return parse(l, i - 1) + parse(i + 1, r);
                            }
                            else if (actions[q] == '-' && (i > l && pos(s.charAt(i - 1)) == -1)) {
                                return parse(l, i - 1) - parse(i + 1, r);
                            }
                            else if (actions[q] == '*') {
                                return parse(l, i - 1) * parse(i + 1, r);
                            }
                            else if (actions[q] == '/') {
                                return parse(l, i - 1) / parse(i + 1, r);
                            }
                        }
                    }
                }
                System.out.println("Str: "+Double.parseDouble(s.substring(l, r + 1)));
                try {
                    return Double.parseDouble(s.substring(l, r + 1));
                } catch (NumberFormatException e) {
                    throw new CalculationException("Calculation error");
                }
            }
        }
    }
    public MegaParser parser = new MegaParser();
    @Override
    public double calculate(String expression) throws CalculationException {
        parser.s = expression;
        return parser.parse(0, expression.length() - 1);
    }
}
