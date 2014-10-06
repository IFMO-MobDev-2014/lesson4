package ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions;

public class Const implements Expression{
	private final double value;

	public Const(double value){
		this.value = value;
	}
	
	public double evaluate(){
		return value;
	}
}
