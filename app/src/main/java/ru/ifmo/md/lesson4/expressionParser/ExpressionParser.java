package ru.ifmo.md.lesson4.expressionParser;

import java.util.*;

public class ExpressionParser {
    private static ArrayList<BinaryOperator> binOperators = new ArrayList<BinaryOperator>();
    private static ArrayList<Function> functions = new ArrayList<Function>();
    private static ArrayList<ChangableWord> vars = new ArrayList<ChangableWord>();
    
    public static Expression3 parse(String str) throws ArithmeticException {
        ArrayList expr = getWords(str);
        
        replaceVariables(expr);
        checkBrackets(expr);
        
        return calcRPN(toRPN(expr));
    }
    
    private static void showMe(ArrayList my) {
        for (int i = 0; i < my.size(); i++) {
            Object cur = my.get(i);
            
            if (cur instanceof Expression3) {
                System.out.print("Expression3 ");
            } else 
            if (cur instanceof BinaryOperator) {
                System.out.print("BinOp ");
            } else 
            if (cur instanceof Function) {
                System.out.print("Function ");
            } else 
            if (cur instanceof Bracket) {
                System.out.print("Bracket ");
            } else {System.out.print("Else ");}
        }
        System.out.println();
    }
    
    private static Expression3 calcRPN(ArrayList expr) {
        Stack stack = new Stack();
        
        for (int i = 0; i < expr.size(); i++) {
            Object cur = expr.get(i);
            if (cur instanceof Expression3) {
                stack.push(cur);
            } else if (cur instanceof BinaryOperator) {
                Expression3 right = (Expression3)stack.pop(), left = (Expression3)stack.pop();
                stack.push(((BinaryOperator)cur).calc(left, right));
            } else if (cur instanceof Function) {
                stack.push(((Function)cur).calc((Expression3)stack.pop()));
            } else {
                throw new ExpressionParseError("Unexpected type of element");
            }
        }
        
        if (stack.size() != 1) {
            throw new ExpressionParseError("Wrong expression");
        }
        
        return (Expression3)stack.back();
    }
    
    private static class Stack {
        private ArrayList stack = new ArrayList();
        public void push(Object elem) { stack.add(elem); }
        public int size() { return stack.size(); }
        public boolean isEmpty() { return stack.size() == 0; }
        public Object pop() { if (isEmpty()) { throw new ExpressionParseError("Wrong expression"); } Object res = stack.get(stack.size() - 1); stack.remove(stack.size() - 1); return res; }
        public Object back(){ if (isEmpty()) { throw new ExpressionParseError("Wrong expression"); } return       stack.get(stack.size() - 1); }
    }
    
    private static ArrayList toRPN(ArrayList expr) {
        Stack stack = new Stack(); 
        ArrayList out = new ArrayList();
        
        for (int i = 0; i < expr.size(); i++) {
            Object cur = expr.get(i);
            
            if (cur instanceof Expression3) {
                out.add(cur);
            } else if (cur instanceof Bracket) {
                if (((Bracket)cur).isOpened) {
                    stack.push(cur);
                } else {
                    while (!(stack.back() instanceof Bracket)) {
                        out.add(stack.pop());
                    }
                    stack.pop();
                }
            } else if (cur instanceof BinaryOperator) {
                while (!stack.isEmpty() 
                      && !(stack.back() instanceof Bracket)
                      && (stack.back() instanceof Function 
                      || ((BinaryOperator)cur).priority 
                            <= 
                                ((BinaryOperator)stack.back()).priority)) {
                    out.add(stack.pop());
                }
                stack.push(cur);
            } else if (cur instanceof Function) {
                stack.push(cur);
            } else {
                throw new ExpressionParseError("Unexpected type of element");
            }
        }
        
        while (!stack.isEmpty()) {
            out.add(stack.pop());
        }
        
        return out;
    }
    
    private static Object replaceVariable(String str) {
        for (int i = 0; i < vars.size(); i++) {
            if (vars.get(i).name.equals(str)) {
                return vars.get(i).calc();
            }
        }
        
        throw new ExpressionParseError("Unexpected variable '" + str + "'");
    }
    
    private static void replaceVariables(ArrayList expr) {
        for (int i = 0; i < expr.size(); i++) {
            if (expr.get(i) instanceof String) {
                expr.set(i, replaceVariable((String)expr.get(i)));
            }
        }
    }
    
    private static ArrayList getWords(String str) {
        WordParser wp = new WordParser(str);
        ArrayList expr = new ArrayList();
        while (wp.hasMoreWords()) expr.add(wp.getNextWord());
        return expr;
    }
    
    private static void checkBrackets(ArrayList expr) {
        int depth = 0;
        
        for (int i = 0; i < expr.size(); i++) {
            if (expr.get(i) instanceof Bracket) {
                if (((Bracket)expr.get(i)).isOpened) {
                    depth++;
                } else {
                    if (depth < 1) {
                        throw new ExpressionParseError("Wrong Expression");
                    }
                    depth--;
                }
            }
        }
        
        if (depth != 0) {
            throw new ExpressionParseError("Wrong Expression");
        }
    }
    
    private static class WordParser {
        String expr;
        int next = 0;
        Types lastType = Types.None;
        
        private enum Types{None, Number, Char, Sign, OpenBr, ClosedBr};
        
        public WordParser(String str) {
            if (str == null) {
                throw new ExpressionParseError("String is empty");
            }
            str = str.replaceAll("\\s", "").toLowerCase();
            
            if (str.equals("")) {
                throw new ExpressionParseError("String is empty");
            }
            
            expr = str;
        }
        
        public boolean hasMoreWords() {
            return next < expr.length();
        }
        
        private Types getType(char ch) {
            if (ch == '(') {
                return Types.OpenBr;
            }
            if (ch == ')') {
                return Types.ClosedBr;
            }
            if ('0' <= ch && ch <= '9' || ch == '.') {
                return Types.Number;
            }
            if ('a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z') {
                return Types.Char;
            }
            for (int i = 0; i < binOperators.size(); i++) {
                if (ch == binOperators.get(i).symbol) {
                    return Types.Sign;
                }
            }
            for (int i = 0; i < functions.size(); i++) {
                if (ch == functions.get(i).symbol) {
                    return Types.Sign;
                }
            }
            return Types.None;
        }
        
        public Object getNextWord() {
            if (!hasMoreWords()) return null;
            int first = next;
            char ch = expr.charAt(next++);
            Types t = getType(ch);
            Object res = null;
            
            switch (t) {
            case Number:
                while (next < expr.length() && getType(expr.charAt(next)) == Types.Number) next++;
                res = new Const(Double.parseDouble(expr.substring(first, next)));
                break;
                
            case Char:
                while (next < expr.length() && getType(expr.charAt(next)) == Types.Char) next++;
                res = expr.substring(first, next);
                break;
                
            case OpenBr:
            case ClosedBr:
                res = new Bracket(t == Types.OpenBr);
                break;
                
            case Sign:
                if (lastType == Types.None || lastType == Types.Sign || lastType == Types.OpenBr) {
                    for (int i = 0; i < functions.size(); i++) {
                        if (ch == functions.get(i).symbol) {
                            res = functions.get(i);
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < binOperators.size(); i++) {
                        if (ch == binOperators.get(i).symbol) {
                            res = binOperators.get(i);
                            break;
                        }
                    }
                }
                break;
            }
            
            if (res != null) {
                lastType = t;
                return res;
            }
            lastType = Types.None;
            throw new ArithmeticException("Unexpected sign '" + ch + "'");
        }
    }
    static {
        binOperators.add(
            new BinaryOperator('+', 1) {
                public Expression3 calc(Expression3 left, Expression3 right) {
                    return new Add(left, right);
                }
            }
        );
        binOperators.add(
            new BinaryOperator('-', 1) {
                public Expression3 calc(Expression3 left, Expression3 right) {
                    return new Subtract(left, right);
                }
            }
        );
        binOperators.add(
            new BinaryOperator('*', 2) {
                public Expression3 calc(Expression3 left, Expression3 right) {
                    return new Multiply(left, right);
                }
            }
        );
        binOperators.add(
            new BinaryOperator('/', 2) {
                public Expression3 calc(Expression3 left, Expression3 right) {
                    return new Divide(left, right);
                }
            }
        );
        binOperators.add(
            new BinaryOperator('%', 2) {
                public Expression3 calc(Expression3 left, Expression3 right) {
                    return new Mod(left, right);
                }
            }
        );
        functions.add(
            new Function('-') {
                public Expression3 calc(final Expression3 expr) {
                    return new UnaryMinus(expr);
                }
            }
        );
        functions.add(
            new Function('+') {
                public Expression3 calc(final Expression3 expr) {
                    return new UnaryPlus(expr);
                }
            }
        );
        /*
        vars.add(
        new ChangableWord("x") {
            public Object calc() {
                return new Variable("x");
            }
        }
        );
        vars.add(
                new ChangableWord("y") {
                    public Object calc() {
                        return new Variable("y");
                    }
                }
        );
        vars.add(
                new ChangableWord("z") {
                    public Object calc() {
                        return new Variable("z");
                    }
                }
        );*/
        vars.add(
            new ChangableWord("mod") {
                public Object calc() {
                    return new BinaryOperator('%', 2) {
                        public Expression3 calc(Expression3 left, Expression3 right) {
                            return new Mod(left, right);
                        }
                    };
                }
            }
        );
    }
}