package ru.ifmo.md.lesson4.parser;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * @author Marianna Bisyarina (bisyarinamariashka@gmail.com)
 */
public class Lexer {

    private int currLexem = 0;
    private int newLexem = 0;
    private String expression;
    private List<Lexem> result;


    public Lexer (String s) throws CalculationException {
        currLexem = 0;
        newLexem = 0;
        expression = s;
        result = new ArrayList<Lexem>();

        while (newLexem < expression.length()) {
            result.add(nextLexem());
        }
    }

    public List<Lexem> getLexems(){
        return result;
    }

    private char currChar() {
        return expression.charAt(currLexem);
    }

    private char newChar() {
        return expression.charAt(newLexem);
    }

    private Lexem nextLexem() throws CalculationException {
        currLexem = newLexem;
        newLexem++;

        switch (currChar()) {
            case '+':
                return new Lexem(Lexem.LexemType.PLUS);

            case '-':
                return new Lexem(Lexem.LexemType.MINUS);

            case '*':
                return new Lexem(Lexem.LexemType.MUL);

            case '/':
                return new Lexem(Lexem.LexemType.DIV);

            case '(':
                return new Lexem(Lexem.LexemType.OPEN_BRACKET);

            case ')':
                return new Lexem(Lexem.LexemType.CLOSE_BRACKET);
        }
        newLexem--;
        if (Character.isDigit(currChar())) {
            getInt();
            if (newLexem < expression.length())
                if (newChar() == '.') {
                    newLexem++;
                    getInt();
                }
            if (newLexem < expression.length())
                if (newChar() == 'e' || newChar() == 'E') {
                    newLexem++;
                    getInt();
                }

            Double curr = Double.parseDouble(expression.substring(currLexem, newLexem));
            return new NumLex(curr);
        }
        throw new CalculationException();
    }

    private void getInt() throws CalculationException {
        if (newLexem < expression.length()) {
            if (newChar() == '-')
                newLexem++;
        } else {
            throw new CalculationException();
        }
        if (newLexem < expression.length()) {
            while (newLexem < expression.length() && Character.isDigit(newChar())) {
                newLexem++;
            }
        } else {
            throw new CalculationException();
        }
    }
}
