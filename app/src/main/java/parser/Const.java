package parser;

public class Const implements Expression3 {
    private double value;
    public Const(double given) {
        value = given;
    }
    public double evaluate(double x, double y, double z) {
        return value;
    }
}
