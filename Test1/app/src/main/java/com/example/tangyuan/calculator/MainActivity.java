 package com.example.tangyuan.calculator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
 public class MainActivity extends AppCompatActivity {
     public static Map pro = new HashMap();
     boolean pointTest = true;
     int k=0;
     Button button1;
     Button button2;
     Button button3;
     Button button4;
     Button button5;
     Button button6;
     Button button7;
     Button button8;
     Button button9;
     Button button;
     Button buttonC;
     Button button_right;
     Button button_left;
     Button button_add;
     Button button_point;
     Button button_sub;
     Button button_mul;
     Button button_div;
     Button button_DEL;
     Button button0;
     TextView textView;
     TextView textView2;
     TextView textView3;
     public static void init() {
         pro.put('+', 1);
         pro.put('-', 1);
         pro.put('*', 2);
         pro.put('/', 2);
     }
     public static int getIndex(String str) {
         int index1 = (str.indexOf('+') == -1 ? str.length() : str.indexOf('+'));
         int index2 = (str.indexOf('-') == -1 ? str.length() : str.indexOf('-'));
         int index3 = (str.indexOf('*') == -1 ? str.length() : str.indexOf('*'));
         int index4 = (str.indexOf('/') == -1 ? str.length() : str.indexOf('/'));
         int index = index1 < index2 ? index1 : index2;
         index = index < index3 ? index : index3;
         index = index < index4 ? index : index4;
         return index;
     }
     public static double cal(char op, double num1, double num2) {
         switch (op) {
             case '+':
                 return num1 + num2;
             case '-':
                 return num1 - num2;
             case '*':
                 return num1 * num2;
             case '/':
                 return num1 / num2;
             default:
                 return 0;
         }
     }
     public static double fun1(String str) {
         init();
         Stack st1 = new Stack();
         Stack st2 = new Stack();
         int fop = 0;
         while (str.length() > 0) {
             int index = getIndex(str);
             st1.push(Double.parseDouble(str.substring(0, index)));
             if (index != str.length()) {
                 char op = str.charAt(index);
                 str = str.substring(index + 1);
                 while (true) {
                     if ((int) pro.get(op) > fop) {
                         st2.push(op);
                         fop = (int) pro.get(op);
                         break;
                     } else {
                         double num2 = (double) st1.pop();
                         double num1 = (double) st1.pop();
                         double result = cal((char) st2.pop(), num1, num2);
                         st1.push(result);
                         if (st2.size() == 0) {
                             st2.push(op);
                             fop = (int) pro.get(op);
                             break;
                         }
                         char cop = (char) st2.pop();
                         fop = (int) pro.get(cop);
                         st2.push(cop);
                     }
                 }
             } else {
                 break;
             }
         }
         while (st2.size() != 0) {
             double num2 = (double) st1.pop();
             double num1 = (double) st1.pop();
             char op = (char) st2.pop();
             st1.push(cal(op, num1, num2));
         }
         double result = (double) st1.pop();
         return result;
     }
     public static double fun2(String str) {
         while (str.indexOf('(') != -1) {
             int left = 0;
             int right = str.length();
             char op;
             for (int i = 0; i < str.length(); i++) {
                 if (str.charAt(i) == '(') {
                     left = i;
                 }
                 if (str.charAt(i) == ')') {
                     right = i;
                     break;
                 }
             }
             str = str.substring(0, left) + fun1(str.substring(left + 1, right)) + str.substring(right + 1);
         }
         return fun1(str);
     }
     public char getLastChar(String equation) {
         String i = "";
         char c;
         if (equation.length() <= 1)
             i = equation;
         else
             i = equation.substring(equation.length() - 1, equation.length());
         c = i.charAt(0);
         return c;
     }
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         button1 = (Button) findViewById(R.id.button);
         button2 = (Button) findViewById(R.id.button2);
         button3 = (Button) findViewById(R.id.button3);
         button4 = (Button) findViewById(R.id.button4);
         button5 = (Button) findViewById(R.id.button5);
         button6 = (Button) findViewById(R.id.button6);
         button7 = (Button) findViewById(R.id.button7);
         button8 = (Button) findViewById(R.id.button8);
         button9 = (Button) findViewById(R.id.button9);
         button = (Button) findViewById(R.id.button10);
         buttonC = (Button) findViewById(R.id.button11);
         button_DEL = (Button) findViewById(R.id.button12);
         button_add = (Button) findViewById(R.id.button13);
         button_point = (Button) findViewById(R.id.button14);
         button_sub = (Button) findViewById(R.id.button15);
         button_mul = (Button) findViewById(R.id.button16);
         button_div = (Button) findViewById(R.id.button17);
         button_left = (Button) findViewById(R.id.button18);
         button_right = (Button) findViewById(R.id.button20);
         button0 = (Button) findViewById(R.id.button19);
         textView = (TextView) findViewById(R.id.textView);
         textView2 = (TextView) findViewById(R.id.textView2);
         textView3 = (TextView) findViewById(R.id.textView3);
         button1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "1";
                     }
                 }else
                    a = a + "1";
                 textView.setText(a);
             }
         });
         button2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "2";
                     }
                 }else
                 a = a + "2";
                 textView.setText(a);
             }
         });
         button3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "3";
                     }
                 }else
                 a = a + "3";
                 textView.setText(a);
             }
         });
         button4.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "4";
                     }
                 }else
                 a = a + "4";
                 textView.setText(a);
             }
         });
         button5.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "5";
                     }
                 }else
                 a = a + "5";
                 textView.setText(a);
             }
         });
         button6.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "6";
                     }
                 }else
                 a = a + "6";
                 textView.setText(a);
             }
         });
         button7.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "7";
                     }
                 }else
                 a = a + "7";
                 textView.setText(a);
             }
         });
         button8.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "8";
                     }
                 }else
                 a = a + "8";
                 textView.setText(a);
             }
         });
         button9.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "9";
                     }
                 }else
                     a = a + "9";
                 textView.setText(a);

             }
         });
         button0.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 if(a.length()>1) {
                     char c = getLastChar(a);
                     if (c != ')') {
                         a = a + "0";
                     }
                 }else
                 a = a + "0";
                 textView.setText(a);
             }
         });
         buttonC.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView3.getText().toString();
                 textView2.setText(a);
                 textView.setText(" ");
                 textView3.setText(" ");
                 k=0;
                 pointTest = true;
             }
         });
         button_DEL.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(textView.getText().length()>0) {

                     String a = textView.getText().toString();
                     char c = getLastChar(a);
                     if(c==')')
                     {
                         k++;
                     }
                     a = a.substring(0, a.length() - 1);
                     textView.setText(a);
                 }
             }
         });
         button_point.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 char c = getLastChar(a);
                 if ((c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                         c == '8' || c == '9')&&pointTest)
                 {
                     a = a + ".";
                     pointTest = false;
                 }
                 else if ((c == ' ' || c == '+' || c == '-' || c == '*' || c == '/')&&pointTest)
                 {
                     pointTest = false;
                     a = a + "0.";
                 }
                 textView.setText(a);
             }
         });
         button_right.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 char c = getLastChar(a);
                 if( (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                         c == '8' || c == '9'|| c == ')')&&k>0)
                 {
                     a = a + ")";
                     k--;
                 }


                 textView.setText(a);
             }
         });
         button_left.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                     char c = getLastChar(a);
                     if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                             c == '8' || c == '9'||c==')')
                     {
                         a = a + "*(";
                         pointTest = true;
                         k++;
                     }
                     else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(')
                     {
                         a = a + "(";
                         pointTest = true;
                         k++;
                     }else if(c== '.')
                     {

                     }else
                     {
                         a = "(";
                         pointTest = true;
                         k++;
                     }
                 textView.setText(a);
             }
         });
         button_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 String b = textView3.getText().toString();
                 char c = getLastChar(a);
                 if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                         c == '8' || c == '9' || c == ')') {
                     a = a + "+";
                     pointTest = true;
                 }
                 else if (a == " " && b != null)
                 {
                     a = b;
                     a = a + "+";pointTest = true;
                 }
                 textView.setText(a);
             }
         });
         button_sub.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 String b = textView3.getText().toString();
                 char c = getLastChar(a);
                 if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                         c == '8' || c == '9' || c == ')')
                 {
                     a = a + "-";
                     pointTest = true;
                 }
                 else if (a == " " && b != null)
                 {
                     a = b;
                     a = a + "-";
                     pointTest = true;
                 }
                 textView.setText(a);
             }
         });

         button_mul.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 String b = textView3.getText().toString();
                 char c = getLastChar(a);
                 if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                         c == '8' || c == '9' || c == ')')
                 {
                     a = a + "*";
                     pointTest = true;
                 }
                 else if (a == " " && b != null)
                 {
                     a = b;
                     a = a + "*";
                     pointTest = true;
                 }
                 textView.setText(a);
             }
         });
         button_div.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String a = textView.getText().toString();
                 String b = textView3.getText().toString();
                 char c = getLastChar(a);
                 if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' ||
                         c == '8' || c == '9' || c == ')') {
                     a = a + "/";
                     pointTest = true;
                 }
                 else if (a == " " && b != null)
                 {
                     a = b;
                     a = a + "/";
                     pointTest = true;
                 }
                 textView.setText(a);
             }
         });
         button.setOnClickListener(new View.OnClickListener() {
                     @Override

                     public void onClick(View v) {
                 if(textView.getText().length()>0) {
                     if( k == 0)
                     {
                         String a = textView.getText().toString();
                         double result = fun2(a);
                         textView3.setText(Double.toString(result));
                     }
                     else
                     {
                         Toast.makeText(MainActivity.this,"括号不匹配",Toast.LENGTH_LONG).show();
                     }

                 }
             }
         });
     }
 }