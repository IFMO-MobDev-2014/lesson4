package ru.ifmo.md.lesson4.tests;

import java.util.Random;

import ru.ifmo.md.lesson4.MyCalcEngine.BinaryOperation;
import ru.ifmo.md.lesson4.MyCalcEngine.Const;
import ru.ifmo.md.lesson4.MyCalcEngine.Expression;
import ru.ifmo.md.lesson4.MyCalcEngine.Operator;

import static ru.ifmo.md.lesson4.MyCalcEngine.Operator.*;


/**
 * @author sugak andrey
 */
public class ExprGenerator {
    public class TestExpression{
        private Expression expression;
        private double answer;

        public TestExpression(Bundle pair) {
            this.setExpression(pair.e);
            this.setAnswer(pair.answer);
        }

        public Expression getExpression() {
            return expression;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }

        public double getAnswer() {
            return answer;
        }

        public void setAnswer(double answer) {
            this.answer = answer;
        }
    }

    private Random rnd = new Random();
    private static Operator[] pool = new Operator[4];
    static {
        pool[0] = ADD;
        pool[1] = DIVIDE;
        pool[2] = MULTIPLY;
        pool[3] = SUBTRACT;
    }

    public TestExpression generateExpression(int recursionDepth){
        if (recursionDepth == 0){
            return new TestExpression(generatePrimitive(null, null, true));
        } else {
            return new TestExpression(generatePrimitive(generateExpression(recursionDepth - 1),
                                                        generateExpression(recursionDepth - 1), false));
        }
    }

    public Bundle generatePrimitive(TestExpression lhs, TestExpression rhs, boolean isPrimitive){
        if (isPrimitive) {
            double value = rnd.nextDouble();
            return new Bundle(new Const(value), value);
        }
        else {
            Operator op = pool[rnd.nextInt(4)];
            double result = 0;
            switch (op){
                case ADD:
                    result = lhs.getAnswer() + rhs.getAnswer();
                    break;
                case MULTIPLY:
                    result = lhs.getAnswer() * rhs.getAnswer();
                    break;
                case SUBTRACT:
                    result = lhs.getAnswer() - rhs.getAnswer();
                    break;
                case DIVIDE:
                    result = lhs.getAnswer() / rhs.getAnswer();
                    break;

            }
            return new Bundle(new BinaryOperation(lhs.getExpression(), rhs.getExpression(), op), result);
        }
    }


    class Bundle {
        Expression e;
        double answer;

        Bundle(Expression e, double answer) {
            this.e = e;
            this.answer = answer;
        }
    }
}
