package ru.ifmo.md.lesson4;

/**
 * Created by pokrasko on 06.10.14.
 */
public class SomeCalculationEngine implements CalculationEngine {

    private static class Stack {
        private class Node {
            private Object value;
            private Node next;

            public Node(Object v, Node n) {
                value = v;
                next = n;
            }
        }

        private int size = 0;
        private Node head;

        void push(Object o) {
            head = new Node(o, head);
            ++size;
        }

        Object peek() {
            assert size > 0;
            return head.value;
        }

        Object pop() {
            assert size > 0;
            Object o = head.value;
            head = head.next;
            --size;
            return o;
        }

        int size() {
            return size;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    private static Stack valStack, opStack;
    private static OperatorType.OpType lastOp;

    private static boolean rightAssociation(char op) {
        return op == '$';
    }

    private static int priority(char op) {
        if (op == '+' || op == '-') {
            return 0;
        } else if (op == '*' || op == '/') {
            return 1;
        } else if (op == '$') {
            return 2;
        } else {
            return -1;
        }
    }

    private static int opSize(char op) {
        if (op == '+' || op == '-' || op == '*' || op == '/') {
            return 2;
        } else if (op == '$') {
            return 1;
        } else {
            return 0;
        }
    }

    private static void getOperation() {
        char op = (Character) opStack.pop();
        if (op == '(') {
            return;
        }
        double first, second;
        if (opSize(op) > 0) {
            second = (Double) valStack.pop();
            if (op == '$') {
                valStack.push(-second);
            }
            if (opSize(op) > 1) {
                first = (Double) valStack.pop();
                if (op == '+') {
                    valStack.push(first + second);
                } else if (op == '-') {
                    valStack.push(first - second);
                } else if (op == '*') {
                    valStack.push(first * second);
                } else if (op == '/') {
                    valStack.push(first / second);
                }
            }
        }
    }

    private static void operationToDo(char op) {
        while (!opStack.isEmpty() && (priority((Character) opStack.peek()) > priority(op)
            || !rightAssociation(op) && priority((Character) opStack.peek()) == priority(op))
            && opSize((Character) opStack.peek()) <= valStack.size()) {
                getOperation();
        }
        opStack.push(op);
    }

    public double calculate(String expression) throws CalculationException {
        opStack = new Stack();
        valStack = new Stack();
        lastOp = OperatorType.OpType.EM;

        for (int i = 0; i < expression.length(); ++i) {
            if (expression.charAt(i) == '(') {
                if (lastOp == OperatorType.OpType.OP || lastOp == OperatorType.OpType.RB) {
                    throw new CalculationException("syntax exception");
                }
                opStack.push('(');
                lastOp = OperatorType.OpType.LB;
            } else if (expression.charAt(i) == ')') {
                if (lastOp != OperatorType.OpType.OP && lastOp != OperatorType.OpType.RB) {
                    throw new CalculationException("syntax exception");
                }
                while (!opStack.isEmpty() && (Character) opStack.peek() != '(') {
                    getOperation();
                }
                if (opStack.isEmpty()) {
                    throw new CalculationException("opened brackets");
                }
                opStack.pop();
                lastOp = OperatorType.OpType.RB;
            } else if (expression.charAt(i) == '+' || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                if (expression.charAt(i) == '~' && (lastOp == OperatorType.OpType.OP ||lastOp == OperatorType.OpType.RB)) {
                    throw new CalculationException("syntax exception");
                } else if (expression.charAt(i) != '~' && lastOp != OperatorType.OpType.OP && lastOp != OperatorType.OpType.RB) {
                    throw new CalculationException("syntax exception");
                }
                operationToDo(expression.charAt(i));
                lastOp = OperatorType.OpType.BO;
            } else if (expression.charAt(i) == '-') {
                if (lastOp != OperatorType.OpType.OP && lastOp != OperatorType.OpType.RB) {
                    operationToDo('$');
                    lastOp = OperatorType.OpType.UO;
                } else {
                    operationToDo('-');
                    lastOp = OperatorType.OpType.BO;
                }
            } else if (Character.isDigit(expression.charAt(i))) {
                if (lastOp == OperatorType.OpType.OP || lastOp == OperatorType.OpType.RB) {
                    throw new CalculationException("syntax exception");
                }
                String numberString;
                StringBuilder build = new StringBuilder();
                boolean dot = false;
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    if (expression.charAt(i) == '.') {
                        if (!dot) {
                            dot = true;
                        } else {
                            throw new CalculationException("too many dots");
                        }
                    }
                    build.append(expression.charAt(i));
                    ++i;
                }
                i--;
                numberString = build.toString();
                assert numberString.length() != 0;
                try {
                    valStack.push(Double.parseDouble(numberString));
                } catch (NumberFormatException e) {
                    throw new CalculationException("double parsing error: " + numberString);
                }
                lastOp = OperatorType.OpType.OP;
            } else if (!Character.isWhitespace(expression.charAt(i))) {
                throw new CalculationException("wrong character found");
            }
        }

        if (lastOp != OperatorType.OpType.OP && lastOp != OperatorType.OpType.RB) {
            throw new CalculationException("syntax exception");
        }
        while (!opStack.isEmpty()) {
            getOperation();
        }
        return (Double) valStack.pop();
    }

}
