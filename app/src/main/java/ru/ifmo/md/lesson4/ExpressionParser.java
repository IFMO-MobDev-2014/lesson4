package ru.ifmo.md.lesson4;
/**
 * Created by Kirill on 05.10.2014.
 */
public class ExpressionParser implements CalculationEngine {
    private  enum TLexem {
        _None,
        _Num,
        _UMinus,
        _UPlus,
        _Plus,
        _Minus,
        _Mul,
        _Div,
        _Open,
        _Close,
        _End
    }

    private class Lexem {
        public TLexem lexem;
        public double val;

        Lexem() {
            lexem = TLexem._None;
            val = 0;
        }

        Lexem(TLexem lex, int v) {
            lexem = lex;
            val = v;
        }
    }

    Lexem curLexem = null;
    Lexem prevLexem = null;
    String expression;
    int nextTokenPointer = 0;

    ExpressionParser() {
        nextTokenPointer = 0;
        curLexem = null;
        prevLexem = null;

    }

    private double parse(String s) throws CalculationException {

        curLexem = null;
        prevLexem = null;
        nextTokenPointer = 0;
        double rv = 0;
        expression = s;
        if (expression.length() == 0)
            expression += '=';
        else if (expression.charAt(expression.length() - 1) != '=')
            expression += '=';
        curLexem = nextLexem();
        rv = expr();
        if (curLexem.lexem != TLexem._End)
            throw new CalculationException("End lexem expected");
        return rv;
    }

    public double calculate(String s) throws CalculationException {
        return parse(s);
    }

    private  void skipSpaces() {
        while (nextTokenPointer < expression.length() && (expression.charAt(nextTokenPointer) == ' '
                || expression.charAt(nextTokenPointer) == '\t')) {
            nextTokenPointer++;
        }
    }

    private  Lexem nextLexem() throws CalculationException{
        skipSpaces();
        prevLexem = curLexem;
        Lexem rv = new Lexem();
        char letter = expression.charAt(nextTokenPointer);
        if (letter == '(')
            rv.lexem = TLexem._Open;
        else if (letter == ')')
            rv.lexem = TLexem._Close;
        else if (letter == '+') {
            if (prevLexem == null ||
                    !(prevLexem.lexem == TLexem._Close ||
                            prevLexem.lexem == TLexem._Num)) {
                rv.lexem = TLexem._UPlus;
            }
            else
                rv.lexem = TLexem._Plus;
        }
        else if (letter == '-') {
            if (prevLexem == null ||
                    !(prevLexem.lexem == TLexem._Close ||
                            prevLexem.lexem == TLexem._Num)) {
                rv.lexem = TLexem._UMinus;
            }
            else
                rv.lexem = TLexem._Minus;
        } else if (letter == '*')
            rv.lexem = TLexem._Mul;
        else if (letter == '/')
            rv.lexem = TLexem._Div;
        else if (letter == '=')
            rv.lexem = TLexem._End;
        else if (letter >= '0' && letter <= '9' || letter == '.') {
            String number = "";
            number += letter;
            int i = nextTokenPointer + 1;
            while ((expression.charAt(i) >= '0' && expression.charAt(i) <= '9')
                    || expression.charAt(i) == '.') {
                number += expression.charAt(i);
                i++;
            }
            rv.lexem = TLexem._Num;
            try {
                rv.val = Double.parseDouble(number);
            } catch (IllegalArgumentException ex) {
                throw new CalculationException("Double parsing error");
            }
            nextTokenPointer = i - 1;
        }
        nextTokenPointer++;
        return rv;
    }

    private  double expr() throws CalculationException {
        double rv = 0;
        rv = item();
        while (curLexem.lexem == TLexem._Plus || curLexem.lexem == TLexem._Minus) {
            Lexem action = curLexem;
            curLexem = nextLexem();
            if (action.lexem == TLexem._Plus) {
                rv = rv + item();
            } else if (action.lexem == TLexem._Minus) {
                rv = rv - item();
            }
        }
        return rv;
    }

    private  double item() throws CalculationException {
        double rv = 0;
        rv = mult();
        while (curLexem.lexem == TLexem._Mul || curLexem.lexem == TLexem._Div) {
            Lexem action = curLexem;
            curLexem = nextLexem();
            if (action.lexem == TLexem._Mul) {
                rv = rv * mult();
            } else if (action.lexem == TLexem._Div) {
                rv = rv / mult();
            }
        }
        return rv;
    }

    private  double mult() throws CalculationException {
        double rv = 0;
        if (curLexem.lexem == TLexem._UMinus) {
            curLexem = nextLexem();
            rv = -1 * mult();
            return rv;
        }
        if (curLexem.lexem == TLexem._UPlus) {
            curLexem = nextLexem();
            rv = mult();
            return rv;
        }

        if (curLexem.lexem == TLexem._Num) {
            rv = curLexem.val;
            curLexem = nextLexem();
        } else if (curLexem.lexem == TLexem._Open) {
            curLexem = nextLexem();
            rv = expr();
            if (curLexem.lexem == TLexem._Close)
                curLexem = nextLexem();
            else
                throw new CalculationException("Close bracket expected");
        }
        else
            throw new CalculationException("Unexpected lexem");
        return rv;
    }
}
