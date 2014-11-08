package ru.ifmo.md.lesson4;

public class ExpressionParser {
    public static void main(String[] args) {

        String t="";
        for (String s: args) {
            t+=s;
        }
       // System.out.println(next(t).str + " " + next(t).value);
        Expression3 a = parse(t);
        System.out.println(a.evaluate(-1098497127, 1, 1));
    }

    public static Expression3 parse(String t) {
        t = t.trim();
        //System.out.println(t);
        Pair n = next(t);
        switch (n.str) {
            case '+' : {
                return new Add(parse(t.substring(0, n.value)), parse(t.substring(n.value+1)));
            }
            case '-' : {
                if (n.value==0) return new Subtract(new Const(0),parse(t.substring(n.value+1)));
                else return new Subtract(parse(t.substring(0, n.value)), parse(t.substring(n.value+1)));
            }
            case '*' : {
                return new Multiply(parse(t.substring(0, n.value)), parse(t.substring(n.value+1)));
            }
            case '/' : {
                return new Divide(parse(t.substring(0, n.value)), parse(t.substring(n.value+1)));
            }
            case 'm' : {
                return new Mod(parse(t.substring(0, n.value)), parse(t.substring(n.value+3)));
            }
            case '(' : {
                return parse(t.substring(1, t.length()-1));
            }
            case 'u' : {
                return new Minus(parse(t.substring(1, t.length())));
            }
            case 'b' : {
                return new BinMin(parse(t.substring(1, t.length())));
            }
            case 'c' : {
                return new Const(n.value);
            }
            default : {
                return new Variable(Character.toString(n.str));
            }
        }
    }

    static Pair next(String s) {
        int i = s.length()-1;
        int j=0;
        char t=' ';
        int tpos=0;
        // looking for + or - (NOT unary -)

       // System.out.println(s);
        while (i >=0) {
            if (Character.isWhitespace(s.charAt(i))) {
                i--;
                continue;
            }
            if (j==0) {
                if (((t == '+') | (t == '-')) & !((s.charAt(i)=='*') | (s.charAt(i)=='/') | (s.charAt(i)=='-'))) {
                   // if (!((i>0) & ((s.charAt(i-1) =='+') | (s.charAt(i-1) =='-') | (s.charAt(i-1) =='*') | (s.charAt(i-1) =='/')))) {
                    if (i>0) {
                        while ((Character.isWhitespace(s.charAt(i)) | (s.charAt(i)=='+') | (s.charAt(i)=='-')) & (i>=0)) {
                            if (Character.isWhitespace(s.charAt(i))) {
                                i--;
                                continue;
                            }
                            t = s.charAt(i);
                            tpos = i;
                            i--;
                        }
                    }
                    return new Pair(t, tpos);
                   // }
                }
            }
            if (s.charAt(i) == '(') j++;
            if (s.charAt(i) == ')') j--;
            t = s.charAt(i);
            tpos = i;
            i--;
        }

        i=s.length()-1;
        j=0;
        while (i >= 0) {
            if (Character.isWhitespace(s.charAt(i))) {
                i--;
                continue;
            }
            if (s.charAt(i) == '(') j++;
            if (s.charAt(i) == ')') j--;

            if (j==0) {
                if ((s.charAt(i) == '*') | (s.charAt(i) == '/') | (s.charAt(i) == 'm')) {
                    return new Pair(s.charAt(i), i);
                }
            }
            i--;
        }

        i=0;
        j=0;
        while (i < s.length()) {
            if (Character.isWhitespace(s.charAt(i))) {
                i++;
                continue;
            }
            if (s.charAt(i) == '(') j++;
            if (s.charAt(i) == ')') j--;
            if (j==0) {
                if (s.charAt(i) == '-') {
                    if (Character.isDigit(s.charAt(i+1))) {
                        return new Pair('c', Integer.parseInt(s));
                    }
                    return new Pair('u', i);
                }
                if (s.charAt(i) == '~') {
                    return new Pair('b', i);
                }
            }
            i++;
        }

        if (s.charAt(0) == '(') {
            return new Pair('(', 0);
        }

        if (Character.isDigit(s.charAt(0))) {
            return new Pair('c', Integer.parseInt(s));
        }

        return new Pair(s.charAt(0), 0);

    }
}