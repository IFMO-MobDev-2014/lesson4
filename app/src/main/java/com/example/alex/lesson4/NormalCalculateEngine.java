package com.example.alex.lesson4;


import android.util.Log;

import com.example.alex.lesson4.CalculationEngine;
import com.example.alex.lesson4.CalculationException;

import java.util.Stack;

public class NormalCalculateEngine implements CalculationEngine {
    @Override
    public double calculate(String expression) throws CalculationException {
        Stack<String> inner = eval(expression);
        return count(inner);
    }


    private static double count(Stack<String> lave) throws CalculationException {
        double op1, op2;
        char ch;
        String ch_str;
        Stack<String> eval = new Stack<String>();
        try {
        while (lave.size() != 0) {
            eval.push(lave.pop());
        }
        while (eval.size() != 0) {
            ch_str = eval.pop();
            ch = ch_str.charAt(0);
            if (isOperator(ch)) {


                switch (ch) {
                    case '+':
                        op2 = Double.parseDouble(lave.pop());
                        op1 = Double.parseDouble(lave.pop());
                        lave.push(Double.toString(op1 + op2));
                        break;
                    case '-':
                        op2 = Double.parseDouble(lave.pop());
                        op1 = Double.parseDouble(lave.pop());
                        lave.push(Double.toString(op1 - op2));
                        break;
                    case '*':
                        op2 = Double.parseDouble(lave.pop());
                        op1 = Double.parseDouble(lave.pop());
                        lave.push(Double.toString(op1 * op2));
                        break;
                    case '/':
                        op2 = Double.parseDouble(lave.pop());
                        op1 = Double.parseDouble(lave.pop());
                        if (op2==0) throw(new CalculationException());
                        lave.push(Double.toString(op1 / op2));
                        break;
                    case '%':
                        op2 = Double.parseDouble(lave.pop());
                        op1 = Double.parseDouble(lave.pop());
                        lave.push(Double.toString(op1 % op2));
                        break;
                    case '@':
                        op1 = Double.parseDouble(lave.pop());
                        lave.push(Double.toString(-op1));
                        break;
                }
            } else {
                lave.push(ch_str);
            }

        }
        ch_str = lave.pop();
        op1= Double.parseDouble(ch_str);}
        catch (Exception e){
            throw (new CalculationException());
        }
        return op1;
    }

    static boolean Probel(char c) {
        if (c == ' ')
            return true;
        return false;
    }

    static boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%'|| c == '@')
            return true;
        return false;
    }

    static int getPriority(char symbol) {
        switch (symbol) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '@':
                return 3;
            default:
                return -1;
        }
    }


    public static Stack<String> eval(String expr) throws CalculationException {
        Log.i("My : ", expr);
        expr = check(expr);



        Stack<String> out = new Stack<String>();
        Stack<String> current = new Stack<String>();//op
        try {

            for (int i = 0; i < expr.length(); i++) {
                char currentSymbol = expr.charAt(i);
                if (Probel(currentSymbol))
                    continue;
                if (currentSymbol == '(')
                    current.push("(");
                else if (currentSymbol == ')') {
                    while (current.peek().charAt(0) != '(')
                        out.push(current.pop());
                    current.pop();
                } else if (isOperator(currentSymbol)) {
                    while (!current.isEmpty() && getPriority(current.peek().charAt(0)) >= getPriority(currentSymbol))
                        out.push(current.pop());
                    String tamp = "";
                    tamp += currentSymbol;
                    current.push(tamp);
                } else {
                    String siq = "";
                    while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.'))
                        siq += expr.charAt(i++);
                    --i;
                    out.push(siq);
                }
            }
            while (!current.isEmpty())
                out.push(current.pop());
        } catch (Exception e){
            throw (new CalculationException());
        }
        return out;
    }


    public static boolean t=true;

    private static String check(String expr) throws CalculationException {
        StringBuffer h = new StringBuffer();
        Log.i("Start ",expr);
        if (expr.charAt(0) == '.' || expr.charAt(expr.length() - 1) == '.'){
            CalculationException e = new CalculationException();
            throw (e);
        }
        for (int i = 1; i < expr.length() - 2; i++) {
            if (expr.charAt(i) == '.' && (!Character.isDigit(expr.charAt(i+1))&&!Character.isDigit(expr.charAt(i-1)))){
                CalculationException e = new CalculationException();
                throw (e);
            }
        }
        for (int i = 0; i < expr.length() - 2; i++) {
            if (expr.charAt(i) == '-' && expr.charAt(i + 1) == '-' && expr.charAt(i + 2) == '-') {
                CalculationException e = new CalculationException();
                throw (e);
            } else {
                if ((Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.') && expr.charAt(i + 1) == '(') {
                    CalculationException e = new CalculationException();
                    throw (e);
                }
            if (t)
            h.append(expr.charAt(i));
            t=true;
            }
        }

        if (expr.length() == 1)if(!Character.isDigit(expr.charAt(expr.length() - 1))){
        CalculationException e = new CalculationException();
            throw (e);} else {
            expr=expr.toString();
            Log.i("End",expr);
            return expr;
        }


        if ((Character.isDigit(expr.charAt(expr.length() - 2)) || expr.charAt(expr.length() - 2) == '.') && expr.charAt(expr.length() - 1) == '(') {
            CalculationException e = new CalculationException();
            throw (e);
        }
        if (t)
        expr=h.append(expr.charAt(expr.length() - 2)).append(expr.charAt(expr.length() - 1)).toString();
         else
            expr=h.append(expr.charAt(expr.length() - 1)).toString();
        h = new StringBuffer();
        char ch;
        if (expr.charAt(0)=='-') h.append('@'); else h.append(expr.charAt(0));
        for (int i = 1; i < expr.length() ; i++){
            ch=expr.charAt(i-1);
            if (expr.charAt(i)=='-'&&(ch=='('||isOperator(ch))) {
                h.append("@");
            }else{
                h.append(expr.charAt(i));
            }
        }
        expr=h.toString();
        Log.i("End",expr);
        return expr;
    }
}