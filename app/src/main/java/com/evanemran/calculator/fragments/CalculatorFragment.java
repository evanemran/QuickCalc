package com.evanemran.calculator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.calculator.listeners.HistoryClickListener;
import com.evanemran.calculator.R;
import com.evanemran.calculator.adapters.RecyclerAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculatorFragment extends Fragment {
    View view;
    TextView textView_output;
    TextView textView_history;
    Button button_ac;
    ImageButton button_clear;
    Button button_equal;
    String firstNum = "";
    String lastNum = "";
    String operator = "";
    RecyclerView recycler_calc;
    RecyclerAdapter adapter;
    List<String> histList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calculator, container, false);
        textView_output = view.findViewById(R.id.textView_output);
        textView_history = view.findViewById(R.id.textView_history);
        button_clear = view.findViewById(R.id.button_clear);
        button_ac = view.findViewById(R.id.button_ac);
        button_equal = view.findViewById(R.id.button_equal);
        recycler_calc = view.findViewById(R.id.recycler_calc);
        initNumberButtons();
        initRecycler();

        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_output.setText("");
                textView_history.setText("");
                operator = "";
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = textView_output.getText().toString();
                if (!val.equals(""))
                {
                    val = val.substring(0, val.length() - 1);
                    textView_output.setText(val);
                }
            }
        });

        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = textView_output.getText().toString();
                if (val.equals("")){
                    return;
                }
                try {
                    textView_output.setText(getResult(val));
                }catch (Exception e){
                    textView_output.setText("Syntax Error!");
                }
                textView_history.setText(/*textView_history.getText().toString() + "\n" +*/ val);
                if (!histList.isEmpty() && val.equals(histList.get(histList.size() - 1))){
                    return;
                }
                histList.add(val);
                adapter = new RecyclerAdapter(getContext(), histList, historyClickListener);
                recycler_calc.setAdapter(adapter);
                recycler_calc.smoothScrollToPosition(histList.size()-1);
                operator = "";

            }
        });

        return view;
    }

    private void initRecycler() {
        histList = new ArrayList<>();
        recycler_calc.setHasFixedSize(true);
        recycler_calc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private String getResult(String equation){
        if (equation.endsWith("+") || equation.endsWith("-") || equation.endsWith("×") || equation.endsWith("÷")){
            return equation;
        }
        String replacedString = equation.replace('÷','/').replace('×', '*');
        double result = eval(replacedString);
        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(result);
    }

    private void initNumberButtons() {
        Button button_number0 = view.findViewById(R.id.button_number0);
        button_number0.setOnClickListener(numberClickListener);
        Button button_number1 = view.findViewById(R.id.button_number1);
        button_number1.setOnClickListener(numberClickListener);
        Button button_number2 = view.findViewById(R.id.button_number2);
        button_number2.setOnClickListener(numberClickListener);
        Button button_number3 = view.findViewById(R.id.button_number3);
        button_number3.setOnClickListener(numberClickListener);
        Button button_number4 = view.findViewById(R.id.button_number4);
        button_number4.setOnClickListener(numberClickListener);
        Button button_number5 = view.findViewById(R.id.button_number5);
        button_number5.setOnClickListener(numberClickListener);
        Button button_number6 = view.findViewById(R.id.button_number6);
        button_number6.setOnClickListener(numberClickListener);
        Button button_number7 = view.findViewById(R.id.button_number7);
        button_number7.setOnClickListener(numberClickListener);
        Button button_number8 = view.findViewById(R.id.button_number8);
        button_number8.setOnClickListener(numberClickListener);
        Button button_number9 = view.findViewById(R.id.button_number9);
        button_number9.setOnClickListener(numberClickListener);

        Button button_div = view.findViewById(R.id.button_div);
        button_div.setOnClickListener(operatorClickListener);
        Button button_mul = view.findViewById(R.id.button_mul);
        button_mul.setOnClickListener(operatorClickListener);
        Button button_sub = view.findViewById(R.id.button_sub);
        button_sub.setOnClickListener(operatorClickListener);
        Button button_add = view.findViewById(R.id.button_add);
        button_add.setOnClickListener(operatorClickListener);
        Button button_dot = view.findViewById(R.id.button_dot);
        button_dot.setOnClickListener(dotClickListener);
        Button button_percent = view.findViewById(R.id.button_percent);
        button_percent.setOnClickListener(percentClickListener);
    }

    private final Button.OnClickListener numberClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            textView_output.setText(textView_output.getText() + view.getTag().toString());
        }
    };

    private final Button.OnClickListener percentClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isGotOperator(textView_output.getText().toString())){
                lastNum = textView_output.getText().toString().substring(textView_output.getText().toString().lastIndexOf(operator)+1);
                lastNum = getResult(firstNum + " * " + lastNum + "/100");
                textView_output.setText(getResult(firstNum + operator + lastNum));
                textView_history.setText(firstNum + operator + lastNum);
                histList.add(firstNum + operator + lastNum);
                adapter = new RecyclerAdapter(getContext(), histList, historyClickListener);
                recycler_calc.setAdapter(adapter);
                recycler_calc.smoothScrollToPosition(histList.size()-1);
            }
        }
    };

    private final View.OnClickListener dotClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                if (operator.equals("")){
                    if(!textView_output.getText().toString().contains("."))
                    {
                        textView_output.setText(textView_output.getText().toString() + ".");
                        return;
                    }
                    return;
                }
                else if (textView_output.getText().toString().substring(textView_output.getText().toString().lastIndexOf(operator)).contains(".")){
                    return;
                }
                textView_output.setText(textView_output.getText().toString() + ".");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private final Button.OnClickListener operatorClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!textView_output.getText().toString().equals("")){
                String equation = textView_output.getText().toString();
                firstNum = getResult(equation);
                operator = view.getTag().toString();
                if (equation.substring(equation.length() - 1).equals(operator)){
                    return;
                }
                if (isOperator(equation.substring(equation.length() - 1))){
                    textView_output.setText(equation.replace(equation.substring(equation.length() - 1), operator));
                    textView_history.setText(equation+view.getTag());
                    return;
                }
                textView_output.setText(equation + operator);
//                histList.add(equation+view.getTag());
//                adapter = new RecyclerAdapter(MainActivity.this, histList, MainActivity.this);
//                recycler_calc.setAdapter(adapter);
            }
        }
    };

    private boolean isOperator (String text){
        return text.equals("+") || text.equals("-") || text.equals("×") || text.equals("÷") || text.equals("%");
    }

    public double eval(final String str) {
        double result = 0;
        try{
            result= new Object() {
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
                        else if (func.equals("log")) x = Math.log10(x);
                        else if (func.equals("ln")) x = Math.log(x);
                        else throw new RuntimeException("Unknown function: " + func);
                    } else {
                        throw new RuntimeException("Unexpected: " + (char)ch);
                    }

                    if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                    return x;
                }
            }.parse();
        }catch (Exception e){
            textView_output.setText("Invalid Syntax!");
        }
        return result;
    }

    public boolean isGotOperator(String equation){
        return equation.contains("+") || equation.contains("-") || equation.contains("×") || equation.contains("÷");
    }

    private final HistoryClickListener historyClickListener = new HistoryClickListener() {
        @Override
        public void onClick(String text) {
            textView_output.setText(text);
        }
    };
}
