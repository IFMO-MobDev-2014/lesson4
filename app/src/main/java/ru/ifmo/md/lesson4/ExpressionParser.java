package ru.ifmo.md.lesson4;

public class ExpressionParser {

	private static String str;
    private static int countOpenBrackets;

	private static Result getMul(int curPos) throws CalculationException {
        if (curPos >= str.length()) {
            throw  new CalculationException();
        }
        if (str.charAt(curPos) == '-') {
			Result res = getMul(curPos + 1);
			res.acc = new UnaryMinus(res.acc);
			return res;
        } else if (Character.isDigit(str.charAt(curPos)) ||
                (str.charAt(curPos) == '-'
                        && curPos + 1 < str.length()
                        && Character.isDigit(str.charAt(curPos + 1)))
                )  {
            int startPos = curPos;
            if (str.charAt(curPos) == '-') {
                curPos++;
            }
            while (curPos < str.length() &&
                    (Character.isDigit(str.charAt(curPos))
                            || str.charAt(curPos) == '.'
                            || str.charAt(curPos) == 'E')
                    ) {
                curPos++;
            }
            try {
                return new Result(new Const((str.substring(startPos, curPos))), curPos);
            } catch (NumberFormatException e) {
                throw new CalculationException();
            }
		} else if (str.charAt(curPos) == '(') {
            countOpenBrackets++;
			return getExpression(curPos + 1);
		} else {
            throw new CalculationException();
        }
	}


	private static Result getTerm(int curPos) throws CalculationException {
        Result res = getMul(curPos);
        curPos = res.curPos;
        while (curPos < str.length() && (str.charAt(curPos) == '*' || str.charAt(curPos) == '/')) {
			char signChar = str.charAt(curPos);
            Result mul = getMul(curPos + 1);
            if (signChar == '*') {
				res.acc = new Multiply(res.acc, mul.acc);
			} else {
				res.acc = new Divide(res.acc, mul.acc);
			}
			curPos = mul.curPos;
			res.curPos = mul.curPos;
		}
		return res;
	}

	private static Result getExpression(int curPos) throws CalculationException {
        Result res = getTerm(curPos);
        curPos = res.curPos;
        while (curPos < str.length() && (str.charAt(curPos) == '+' || str.charAt(curPos) == '-')) {
			char signChar = str.charAt(curPos);
            Result term = getTerm(curPos + 1);
            if (signChar == '+') {
				res.acc = new Add(res.acc, term.acc);
			} else {
				res.acc = new Subtract(res.acc, term.acc);
			}
			res.curPos = term.curPos;
			curPos = term.curPos;
		}
		if (curPos < str.length() && str.charAt(curPos) == ')') {
            if (countOpenBrackets > 0) {
                countOpenBrackets--;
            } else {
                throw new CalculationException();
            }
			curPos++;
		}
		res.curPos = curPos;
		return res;
	}


	public static Expression parse(String expression) throws CalculationException {
        str = expression.replaceAll("\\s", "");
        countOpenBrackets = 0;
		return getExpression(0).acc;
	}
			

}
