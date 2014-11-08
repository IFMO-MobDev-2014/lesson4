package parser;

public class Variable implements Expression3 {
    String value;
    public Variable(String name) {
        value = name;
    }
    public double evaluate(double x, double y, double z) {
        if (value.charAt(0) == 'x') {
            return x;
        }
        else {
            if (value.charAt(0) == 'y') {
                return y;
            }
            else {
                return z;
            }
        }
    }
}