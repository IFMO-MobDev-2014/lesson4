package ru.ifmo.md.lesson4.parser;

import java.util.List;

import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.expr.Const;
import ru.ifmo.md.lesson4.expr.Expression;
import ru.ifmo.md.lesson4.expr.Negate;

/**
 * Created by mariashka on 10/5/14.
 */
public class Parser {

    private List<Lexem> lexems;
    private int idx;

    public Parser(String expression) throws CalculationException {
        idx = 0;
        Lexer curr = new Lexer(expression);
        lexems = curr.getLexems();
    }


    public Expression parse() throws CalculationException {
        Expression result = evalLexem(1);
        if (idx < lexems.size())
            throw new CalculationException();
        return result;
    }

    private Expression evalLexem(int priority) throws CalculationException {
        if (priority > 2) {
            return lexem();
        }

        Expression result;
        result = evalLexem(priority + 1);

        while (idx < lexems.size() && (lexems.get(idx).hasPrior(priority))) {
            Lexem prevIdx = lexems.get(idx);
            idx++;
            result = prevIdx.getBinaryExpr(result, evalLexem(priority + 1));
        }
        return result;
    }

    private Expression lexem() throws CalculationException {
        if (idx >= lexems.size())
            throw new CalculationException();

        Expression result;
        Lexem.LexemType lexemType = lexems.get(idx).type;

        switch (lexemType) {
            case CONST:
                result = new Const(((NumLex) lexems.get(idx)).getValue());
                idx++;
                break;

            case MINUS:
                idx++;
                result = new Negate(lexem());
                break;

            case OPEN_BRACKET:
                idx++;
                result = evalLexem(1);
                if (idx < lexems.size() && lexems.get(idx).type == Lexem.LexemType.CLOSE_BRACKET)
                    idx++;
                else
                    throw new CalculationException();
                break;

            default:
                throw new CalculationException();
        }
        return result;
    }
}
