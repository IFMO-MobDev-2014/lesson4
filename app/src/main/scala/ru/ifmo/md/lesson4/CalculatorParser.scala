package ru.ifmo.md.lesson4

import org.parboiled2._
// It *IS* permitted to use third-party libraries, but default scala parser combinators are slow so i see no difference here.
// (You may be sure i can use default too)
// Moreover, I have this written: https://github.com/volhovM/open-calc/blob/master/src/ru/volhovm/calc/calclib/parser/ExpressionParser.java
class CalculatorParser(val input: ParserInput) extends Parser {
  // Grammar
  // A -> B {"+" B}* | B {"-" B}*
  // B -> C {"*" C}* | C {"-" C}*
  // C -> {D "^"}* D
  // D -> Number | "-" D | "(" A ")"

  def inputLine: Rule1[Double] = rule {first}
  private def first: Rule1[Double] =
    rule { second ~ zeroOrMore("+" ~ second ~> ((_:Double) + _) | "-" ~ second ~> ((_:Double) - _)) }
  private def second: Rule1[Double] =
    rule { third ~ zeroOrMore(
    "*" ~ third ~> ((_:Double) * _) |
    "/" ~ third ~> ((_:Double) / _)
    ) }
  private def third: Rule1[Double] = rule { oneOrMore(fourth).separatedBy("^") ~> ((a: Seq[Double]) => a.reduceRight(math.pow)) }
  private def fourth: Rule1[Double] = rule {spaces ~ (number | parenth | ("-" ~ third ~> ((a: Double) => -a))) ~ spaces}
  private def spaces = rule { zeroOrMore(anyOf(" ")) }
  private def number = rule { capture(oneOrMore(anyOf("0123456789e."))) ~> (_.toDouble)}
  private def parenth = rule { "(" ~ first ~ ")" }
}