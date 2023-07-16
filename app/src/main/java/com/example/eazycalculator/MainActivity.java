package com.example.eazycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resulttv,solutv;
    MaterialButton buttonC,buttonBracOpen,buttonBracClose;
    MaterialButton button9,button8,button7,button6,button5,button4,button3,button2,button1,button0;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton buttonAC,buttondot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resulttv = findViewById(R.id.result_tv);
        solutv = findViewById(R.id.solution_tv);

        assignId(buttonC,R.id.button_c);
        assignId(button0,R.id.button_0);
        assignId(button9,R.id.button_9);
        assignId(button8,R.id.button_8);
        assignId(button7,R.id.button_7);
        assignId(button6,R.id.button_6);
        assignId(button5,R.id.button_5);
        assignId(button4,R.id.button_4);
        assignId(button3,R.id.button_3);
        assignId(button2,R.id.button_2);
        assignId(button1,R.id.button_1);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonPlus,R.id.button_addition);
        assignId(buttonMinus,R.id.button_subtraction);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonBracClose,R.id.button_close_bracket);
        assignId(buttonBracOpen,R.id.button_open_bracket);
        assignId(buttonEquals,R.id.button_equal);
        assignId(buttondot,R.id.button_point);
    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutv.getText().toString();

        if (buttonText.equals("AC")) {
            solutv.setText("");
            resulttv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutv.setText(resulttv.getText());
            return;
        }
        try {
            if (buttonText.equals("C")) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        }catch(Exception e){
            resulttv.setText("0");
        }
        solutv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Err")) {
            resulttv.setText(finalResult);
        }

    }
    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "javascript", 1, null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e) {
            return "Err";
        }
    }
}
