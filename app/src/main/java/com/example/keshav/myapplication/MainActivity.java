package com.example.keshav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    TextView tvDot,tvZero,tvOne,tvExp,tvTwo,tvThree,tvFour,tvFive,tvSix,tvSeven,tvEight,tvNine,tvResult,tvPlus,tvMinus,tvMul,tvDiv,
            tvEqual,tvClear,tvOpen,tvClose;
    ImageView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appendExp();
        onButtonClick();
    }
       public void onButtonClick(){
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvExp.setText(tvExp.getText()+"1");
            }
        });
           tvTwo.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"2");
               }
           });
           tvThree.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"3");
               }
           });
           tvFour.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"4");
               }
           });
           tvFive.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"5");
               }
           });
           tvSix.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"6");
               }
           });
           tvSeven.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"7");
               }
           });
           tvEight.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"8");
               }
           });
           tvNine.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"9");
               }
           });
           tvZero.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"0");
               }
           });
           tvDot.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+".");
               }
           });
           tvPlus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"+");
               }
           });
           tvMinus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText(tvExp.getText()+"-");
               }
           });
           tvMul.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String s=tvExp.getText().toString();
                   if (validate(s))
                   tvExp.setText(tvExp.getText()+"*");
               }
           });
           tvDiv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String s=tvExp.getText().toString();
                   if (validate(s))
                   tvExp.setText(tvExp.getText()+"/");
               }
           });
           tvEqual.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String val=eval(tvExp.getText().toString())+"";
                  tvExp.setText(val.endsWith(".0")?val.substring(0,val.lastIndexOf(".0")):val);
               }
           });
           tvClear.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tvExp.setText("");
               }
           });
           tvBack.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String s=tvExp.getText().toString();
                   if (validate(s))
                   tvExp.setText(s.substring(0,s.length()-1));
               }
           });
           tvOpen.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   tvExp.setText(tvExp.getText()+"(");
               }
           });
           tvClose.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String s=tvExp.getText().toString();
                   if (validate(s))
                   tvExp.setText(tvExp.getText()+")");
               }
           });
        }
    public boolean validate(String s){
        if(s.isEmpty())
            return false;
       /* try{
            Pattern p=Pattern.compile("^([-+]?[0-9]*\\.?[0-9]+[\\/\\+\\-\\*])+([-+]?[0-9]*\\.?[0-9]+)$");
            Matcher m=p.matcher(s);
            if(m.find()) return true;

        }catch(Exception e){
            return false;
        }*/
        return true;
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }


            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
    public void appendExp(){
          tvExp=(TextView)findViewById(R.id.tvExp);
        tvResult=(TextView)findViewById(R.id.tvResult);
        tvDot=(TextView)findViewById(R.id.tvDot);
        tvZero=(TextView)findViewById(R.id.tvZero);
          tvOne=(TextView)findViewById(R.id.tvOne);
        tvTwo=(TextView)findViewById(R.id.tvTwo);
        tvThree=(TextView)findViewById(R.id.tvThree);
        tvFour=(TextView)findViewById(R.id.tvFour);
        tvFive=(TextView)findViewById(R.id.tvFive);
        tvSix=(TextView)findViewById(R.id.tvSix);
        tvSeven=(TextView)findViewById(R.id.tvSeven);
        tvEight=(TextView)findViewById(R.id.tvEight);
        tvNine=(TextView)findViewById(R.id.tvNine);
        tvPlus=(TextView)findViewById(R.id.tvPlus);
        tvMinus=(TextView)findViewById(R.id.tvMinus);
        tvMul=(TextView)findViewById(R.id.tvMul);
        tvDiv=(TextView)findViewById(R.id.tvDiv);
        tvEqual=(TextView)findViewById(R.id.tvEqual);
        tvClear=(TextView)findViewById(R.id.tvClear);
        tvBack=(ImageView)findViewById(R.id.tvBack);
        tvOpen=(TextView)findViewById(R.id.tvOpen);
        tvClose=(TextView)findViewById(R.id.tvClose);
    }
}
