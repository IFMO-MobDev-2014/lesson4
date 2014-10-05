package ru.ifmo.md.lesson4

object CalculatorWrapper {
  def get(input: String): Double = new CalculatorParser(input).inputLine.run().getOrElse(throw new CalculationException())
}