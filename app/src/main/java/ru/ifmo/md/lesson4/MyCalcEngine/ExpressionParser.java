package ru.ifmo.md.lesson4.MyCalcEngine;
/**
 * @author sugak andrey
 */

import ru.ifmo.md.lesson4.CalculationException;

import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.CB;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.CONST;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.DIVISION;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.EOI;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.MINUS;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.OB;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.PLUS;
import static ru.ifmo.md.lesson4.MyCalcEngine.ExpressionParser.Token.TIMES;

public class ExpressionParser {
    private String expression;
    private int index;
    private Token token;
    private double number;

    enum Token {
        PLUS,
        MINUS,
        TIMES,
        DIVISION,
        OB,
        CB,
        EOI,
        CONST
    }


    private void nextToken(){
        while (index < expression.length() && String.valueOf(expression.charAt(index)).matches("\\s")){
            index++;
        }
        if (index < expression.length()){
            char c = expression.charAt(index);
            index++;
            if ((String.valueOf(c)).matches("[0-9]")){
                StringBuilder acc = new StringBuilder();
                acc.append(c);
                while (index < expression.length()
                        && String.valueOf(expression.charAt(index)).matches("[[0-9] | \\. | E]")){
                    acc.append(expression.charAt(index));
                    index++;
                }
                try {
                    number = Double.parseDouble(acc.toString());
                } catch (NumberFormatException e) {
                    throw new ParseException(acc.toString() + " is not a valid double precision number");
                }
                token = CONST;
                return;
            }
            switch (c){
                case '-':
                    token =  MINUS;
                    return;
                case '+':
                    token = PLUS;
                    return;
                case '/':
                    token = DIVISION;
                    return;
                case '*':
                    token = TIMES;
                    return;
                case '(':
                    token = OB;
                    return;
                case ')':
                    token = CB;
                    return;
            }
        }
        token = EOI;
    }

    public Expression parse(String expression) throws CalculationException {
        this.expression = expression;
        index = 0;
        Expression result;
        try {
            nextToken();
            result = parseAddSub();
        } catch (ParseException e) {
            throw new CalculationException();
        }
        try {
            nextToken();
        } catch (Exception e) {
            throw new CalculationException();
        }
        if (token != EOI)
            throw new CalculationException();
        return result;

    }

    private Expression parseAddSub() {
        Expression lhs = parseDivMul();
        while (token == PLUS || token == MINUS) {
            Operator operator = token == PLUS ? Operator.ADD : Operator.SUBTRACT;
            nextToken();
            Expression rhs = parseDivMul();
            lhs = new BinaryOperation(lhs, rhs, operator);
        }
        return lhs;
    }

    private Expression parseDivMul() {
        Expression lhs = parseConst();
        while (token == TIMES || token == DIVISION) {
            Operator operator = token == TIMES ? Operator.MULTIPLY : Operator.DIVIDE;
            nextToken();
            Expression rhs = parseConst();
            lhs = new BinaryOperation(lhs, rhs, operator);
        }
        return lhs;
    }

    private Expression parseConst() {
        if (token == MINUS){
            nextToken();
            return new BinaryOperation(new Const(0), parseConst(), Operator.SUBTRACT);
        }
        if (token == PLUS){
            nextToken();
            Expression parsed = parseConst();
            return parsed;
        }
        if (token == OB){
            nextToken();
            Expression tmp = parseAddSub();
            if (token == CB) {
                nextToken();
                return tmp;
            } else {
                throw new ParseException("Closing bracket expected but  " + token + " found");
            }
        }
        if (token == CONST) {
            nextToken();
            return new Const(number);
        }
        nextToken();
        throw new ParseException("Expected numeral but " + token + " found");
    }

}
