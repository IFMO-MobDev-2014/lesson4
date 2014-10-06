package ru.ifmo.md.lesson4;
import javax.script.*;
/**
 * Created by default on 06.10.14.
 */

public class MagicCalculationEngine implements CalculationEngine {

    public double calculate(String expression) throws CalculationException{
        try {
            javax.script.ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            return (Double) engine.eval(expression);
        }
        catch (ScriptException e){
            return Double.NaN;
        }
    }
}
