package ru.ifmo.md.lesson4

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.{Bundle, Vibrator}
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.{Button, TextView, Toast}
import org.parboiled2.Parser.DeliveryScheme.Try

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
      override def onLongClick(p1: View): Boolean = {
        vib.vibrate(10); clear(); true
      }
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
    if (clearNeeded) {
      clearNeeded = false; clear()
    }
    textView.setText(str())
  }
  def addSymbol(str: String) = setTx({ () => textView.getText + str})
  def setTemp(str: String) = {
    setTx(str); clearNeeded = true
  }
  def clear(): Unit = setTx("")
  def removeSymbol() =
    setTx(if (!clearNeeded && textView.getText != "") textView.getText.subSequence(0, textView.getText.length - 1)
    else {
      clearNeeded = false; ""
    })

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

  def onCalcButtonPress(view: View) = {
    val viewCur = cast[View, Button](view)
    val buttonEq = resources.getString(R.string.buttonEq)
    val buttonDel = resources.getString(R.string.buttonDel)
    if (viewCur != null) viewCur.getText match {
      case `buttonEq` => eval()
      case `buttonDel` => removeSymbol()
      case a if a != "" => addSymbol(a.toString)
      case _ =>
    }
  }
}