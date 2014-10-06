package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    DummyCalculateEngine parser;
    List<Character> isSign = Arrays.asList('+', '-', '*', '/');
    String expression = "";
    int i, openBrackets = 0;
    boolean mathSign = false;
    boolean digitSign = false;
    TextView text;

    private void addDigit(int num) {
        if (!digitSign) {
            if (expression.length() == 0 || expression.charAt(expression.length() - 1) != ')') {
                expression += num;
            } else {
                expression = expression.substring(0, expression.length() - 1) + num + ")";
            }
            mathSign = false;
            text.setText(expression);
        }
    }

    private void addSign(char sign) {
        if (!mathSign) {
            expression += sign;
            text.setText(expression);
            mathSign = true;
            digitSign = false;
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button digit[] = new Button[10];
        digit[0] = (Button) findViewById(R.id.digit0);
        digit[1] = (Button) findViewById(R.id.digit1);
        digit[2] = (Button) findViewById(R.id.digit2);
        digit[3] = (Button) findViewById(R.id.digit3);
        digit[4] = (Button) findViewById(R.id.digit4);
        digit[5] = (Button) findViewById(R.id.digit5);
        digit[6] = (Button) findViewById(R.id.digit6);
        digit[7] = (Button) findViewById(R.id.digit7);
        digit[8] = (Button) findViewById(R.id.digit8);
        digit[9] = (Button) findViewById(R.id.digit9);

        Button add = (Button) findViewById(R.id.addition);
        Button subtract = (Button) findViewById(R.id.subtraction);
        Button multiply = (Button) findViewById(R.id.multiplication);
        Button divide = (Button) findViewById(R.id.division);
        Button dot = (Button) findViewById(R.id.dot);
        Button leftBracket = (Button) findViewById(R.id.leftBracket);
        Button rightBracket = (Button) findViewById(R.id.rightBracket);
        Button clearField = (Button) findViewById(R.id.clear);
        Button delToken = (Button) findViewById(R.id.delete);
        Button calculate = (Button) findViewById(R.id.calculation);
        text = (TextView) findViewById(R.id.textView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    addSign('+');
                }
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    addSign('-');
                }
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    addSign('*');
                }
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    addSign('/');
                }
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() > 0 && Character.isDigit(expression.charAt(expression.length() - 1))) {
                    expression += ".";
                    mathSign = true;
                    text.setText(expression);
                }
            }
        });

        for (i = 0; i < 10; i++) {
            digit[i].setOnClickListener(new View.OnClickListener() {
                final int x = i;

                @Override
                public void onClick(View v) {
                    addDigit(x);
                }
            });
        }

        leftBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0 ||
                        isSign.contains(expression.charAt(expression.length() - 1)) || expression.charAt(expression.length() - 1) == '(') {
                    openBrackets++;
                    expression += "(";
                    text.setText(expression);
                    mathSign = true;
                    digitSign = false;
                }
            }
        });

        rightBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openBrackets > 0 &&
                        (Character.isDigit(expression.charAt(expression.length() - 1)) || expression.charAt(expression.length() - 1) == ')')) {
                    openBrackets--;
                    expression += ")";
                    digitSign = true;
                    mathSign = false;
                    text.setText(expression);
                }
            }
        });

        delToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expression.isEmpty()) {
                    char c = expression.charAt(expression.length() - 1);
                    expression = expression.substring(0, expression.length() - 1);
                    if (c == ')')
                        openBrackets++;
                    else if (c == '(')
                        openBrackets--;
                    else if (isSign.contains(c)) {
                        mathSign = false;
                        if (expression.length() == 0 || Character.isDigit(expression.length() - 1))
                            digitSign = false;
                        else if (expression.charAt(expression.length() - 1) == ')') {
                            int j = expression.length() - 1;
                            while (j >= 0 && Character.isDigit(expression.charAt(j))) j--;
                            if (j >= 1 && expression.charAt(j) == '-' && expression.charAt(j - 1) == '(')
                                digitSign = false;
                        }
                    }
                    text.setText(expression);
                }
            }
        });

        clearField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                openBrackets = 0;
                digitSign = false;
                mathSign = false;
                text.setText(expression);
            }
        });

        parser = CalculationEngineFactory.defaultEngine();
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < openBrackets; i++)
                        expression += ")";
                    expression = Double.toString(parser.calculate(expression));

                    if (expression.charAt(expression.length() - 1) == '0') {
                        expression = expression.substring(0, expression.length() - 2);
                    }

                    text.setText(expression);

                } catch (CalculationException e) {
                    text.setText(e.getMessage());
                    expression = "";
                } catch (Exception e) {
                    expression = "";
                    text.setText(" unparsable :(");
                }
                mathSign = false;
            }
        });

    }
}