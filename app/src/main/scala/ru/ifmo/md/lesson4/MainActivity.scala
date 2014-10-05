package ru.ifmo.md.lesson4

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.{Bundle, Vibrator}
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.{Button, TextView, Toast}
import org.parboiled2.Parser.DeliveryScheme.Try

import scala.annotation.switch
import scala.util.{Failure, Success}

class MainActivity extends Activity {
  private var textView: TextView = null
  private var resources: Resources = null
  var vib: Vibrator = null
  var clearNeeded: Boolean = false

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    resources = getResources
    vib = cast[AnyRef, Vibrator](this.getSystemService(Context.VIBRATOR_SERVICE))
    cast[View, Button](findViewById(R.id.button2), null).setOnLongClickListener(new OnLongClickListener {
        override def onLongClick(p1: View): Boolean = { vib.vibrate(10); clear(); true }
    })
    textView = cast[View, TextView](findViewById(R.id.calcTextField))
  }

  def cast[A, B](expr: A, other: B = null): B = expr match {
    case s: B => s
    case _ => other
  }

  // I can't make it functional, it's too much java methods
  // (sad)
  implicit def wrap[A](a: A): () => A = () => a
  def setTx(str: () => CharSequence) = {
    if (clearNeeded) { clearNeeded = false; clear() }
    textView.setText(str())
  }
  def addSymbol(str: String) = setTx({() => textView.getText + str})
  def setTemp(str: String) = { setTx(str); clearNeeded = true }
  def clear(): Unit = setTx("")
  def removeSymbol() =
    setTx(if (!clearNeeded && textView.getText != "") textView.getText.subSequence(0, textView.getText.length - 1)
    else {clearNeeded = false; ""})

  // Unsafe with exceptions (uurgh!)
  @deprecated def evalJava(): Unit =
    setTx(CalculationEngineFactory.defaultEngine().calculate(textView.getText.toString).toString)

  // We like Try so much!
  def eval(): Unit =
    if (textView.getText.length > 0) setTemp(new CalculatorParser(textView.getText.toString).inputLine.run() match {
      case Success(a) => a.toString
      case Failure(exc) =>
        val a = Toast.makeText(this, "Error:" + exc.toString + ": " + exc.getMessage, Toast.LENGTH_SHORT)
        a.show()
        ""
    })

  // That's disgusting. I've come up with no idea how to make it better though :c
  // Please tell me if I miss something.
  def onCalcButtonPress(view: View): Unit = (view.getId: @switch) match {
    case R.id.button0 => addSymbol(resources.getString(R.string.button0))
    case R.id.button1 => addSymbol(resources.getString(R.string.button1))
    case R.id.button2 => removeSymbol()
    case R.id.button3 => eval()
    case R.id.button4 => addSymbol(resources.getString(R.string.button4))
    case R.id.button5 => addSymbol(resources.getString(R.string.button5))
    case R.id.button6 => addSymbol(resources.getString(R.string.button6))
    case R.id.button7 => addSymbol(resources.getString(R.string.button7))
    case R.id.button8 => addSymbol(resources.getString(R.string.button8))
    case R.id.button9 => addSymbol(resources.getString(R.string.button9))
    case R.id.button10 => addSymbol(resources.getString(R.string.button10))
    case R.id.button11 => addSymbol(resources.getString(R.string.button11))
    case R.id.button12 => addSymbol(resources.getString(R.string.button12))
    case R.id.button13 => addSymbol(resources.getString(R.string.button13))
    case R.id.button14 => addSymbol(resources.getString(R.string.button14))
    case R.id.button15 => addSymbol(resources.getString(R.string.button15))
    case R.id.button16 => addSymbol(resources.getString(R.string.button16))
    case R.id.button17 => addSymbol(resources.getString(R.string.button17))
    case R.id.button18 => addSymbol(resources.getString(R.string.button18))
    case R.id.button19 => addSymbol(resources.getString(R.string.button19))
  }
}