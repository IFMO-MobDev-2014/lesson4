package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {

       private class Result {
           public double acc;
           public int rest;

           public Result(double v, int r) {
               this.acc = v;
               this.rest = r;
           }
       }

       String str;

       @Override
       public double calculate(String expression) throws CalculationException {
           str = expression;
           try {
               return expressionParse(0).acc;
           } catch (Exception e) {
               throw new CalculationException();
           }
       }

       private Result expressionParse(int x) throws CalculationException {
           Result cur1 = termParse(x);
           while (cur1.rest < str.length() && str.charAt(cur1.rest) != ')') {
               while (cur1.rest < str.length() && Character.isWhitespace(str.charAt(cur1.rest))) cur1.rest++;
               char sign = str.charAt(cur1.rest);
               Result cur2 = termParse(cur1.rest + 1);
               if (sign == '+') {
                   cur1.acc = cur1.acc + cur2.acc;
               } else if (sign == '-') {
                   cur1.acc = cur1.acc - cur2.acc;
               } else throw new CalculationException();
               cur1.rest = cur2.rest;
           }
           return new Result(cur1.acc, cur1.rest);
       }

       private Result termParse(int x) throws CalculationException {
           Result cur1 = multiplierParse(x);
           while (cur1.rest < str.length() && (str.charAt(cur1.rest) == '*' || str.charAt(cur1.rest) == '/')) {
               while (cur1.rest < str.length() && Character.isWhitespace(str.charAt(cur1.rest))) cur1.rest++;
               char sign = str.charAt(cur1.rest);
               Result cur2 = multiplierParse(cur1.rest + 1);
               if (sign == '*') {
                   cur1.acc = cur1.acc * cur2.acc;
               } else if (sign == '/') {
                   if (Math.abs(cur2.acc) < 1E-9) throw new CalculationException();
                   cur1.acc = cur1.acc / cur2.acc;
               } else throw new CalculationException();
               cur1.rest = cur2.rest;
           }
           return new Result(cur1.acc, cur1.rest);
       }

       private Result multiplierParse(int x) throws CalculationException {
           while (x < str.length() && Character.isWhitespace(str.charAt(x))) x++;
           if (str.charAt(x) == '-') {
               Result cur = multiplierParse(x + 1);
               return new Result(-cur.acc, cur.rest);
           } else if (str.charAt(x) == '+') {
               Result cur = multiplierParse(x + 1);
               return new Result(+cur.acc, cur.rest);
           } else if (Character.isDigit(str.charAt(x))) {
               return numParse(x);
           } else {
               if (str.charAt(x) != '(') throw new CalculationException();
               Result cur = expressionParse(x + 1);
               cur.rest++;
               while (cur.rest < str.length() && Character.isWhitespace(str.charAt(cur.rest))) cur.rest++;
               return new Result(cur.acc, cur.rest);
           }
       }

       private Result numParse(int x) throws CalculationException {
           int i = x;
           String f = "";
           boolean point = false;
           while (i < str.length() && (Character.isDigit(str.charAt(i)) || Character.isWhitespace(str.charAt(i)) || str.charAt(i) == '.') ) {
               if (Character.isDigit(str.charAt(i))) f += str.charAt(i);
               if (str.charAt(i) == '.') {
                   if (point) throw new CalculationException();
                   f += '.';
                   point = true;
               }
               i++;
           }
           return new Result(Double.parseDouble(f), i);
       }
}
