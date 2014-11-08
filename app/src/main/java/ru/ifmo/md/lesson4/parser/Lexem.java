package ru.ifmo.md.lesson4.parser;

import java.lang.*;
import java.util.ArrayList;

import ru.ifmo.md.lesson4.expr.Add;
import ru.ifmo.md.lesson4.expr.Divide;
import ru.ifmo.md.lesson4.expr.Expression;
import ru.ifmo.md.lesson4.expr.Multiply;
import ru.ifmo.md.lesson4.expr.Subtract;

/**
 * @author Marianna Bisyarina (bisyarinamariashka@gmail.com)
 */
public class Lexem {
    protected final LexemType type;
    protected static final ArrayList<LexemType>[] operation = new ArrayList[6];

    static {

        for (int i = 0; i < 3; i++) {
            operation[i] = new ArrayList<LexemType>();
        }

        operation[1].add(LexemType.PLUS);
        operation[1].add(LexemType.MINUS);

        operation[2].add(LexemType.MUL);
        operation[2].add(LexemType.DIV);
    }

    public Lexem(LexemType type) {
        this.type = type;
    }

    public static enum LexemType{
        CONST,
        PLUS,
        MINUS,
        MUL,
        DIV,
        OPEN_BRACKET,
        CLOSE_BRACKET,
    }


    public boolean hasPrior(int priority) {
        for (int i = 0; i < operation[priority].size(); i++) {
            if (operation[priority].get(i) == this.type)
                return true;
        }
        return false;
    }

    public Expression getBinaryExpr(Expression a, Expression b){
        if (type == LexemType.PLUS)
            return new Add(a, b);
        else if (type == LexemType.MINUS)
            return new Subtract(a, b);
        else if (type == LexemType.MUL)
            return new Multiply(a, b);
        else if (type == LexemType.DIV)
            return new Divide(a, b);

        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return "LexemType: " + type;
    }

    public LexemType getType() {

        return type;
    }
}
