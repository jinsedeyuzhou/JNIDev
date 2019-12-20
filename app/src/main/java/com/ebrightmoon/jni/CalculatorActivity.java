package com.ebrightmoon.jni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ebrightmoon.jni.calculator.Calculator;

/**
 * Time: 2019-11-25
 * Author:wyy
 * Description:
 */
public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_input;
    private EditText et_input_next;
    private Button btn_add;
    private Button btn_sub;
    private Button btn_mul;
    private Button btn_div;
    private TextView tv_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {

        et_input = findViewById(R.id.et_input);
        et_input_next = findViewById(R.id.et_input_next);
        btn_add = findViewById(R.id.btn_add);
        btn_sub = findViewById(R.id.btn_sub);
        btn_mul = findViewById(R.id.btn_mul);
        btn_div = findViewById(R.id.btn_div);
        tv_result = findViewById(R.id.tv_result);

        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_div.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int result=0;
        String str1=et_input.getText().toString();
        String str2=et_input_next.getText().toString();
        int a=Integer.parseInt(str1);
        int b=Integer.parseInt(str2);
        switch (v.getId()) {
            case R.id.btn_add:
                result=Calculator.add(a,b);
                break;
            case R.id.btn_sub:
                result=Calculator.sub(a,b);
                break;
            case R.id.btn_mul:
                result=Calculator.mul(a,b);
                break;
            case R.id.btn_div:
                result=Calculator.div(a,b);
                break;
            default:
        }

        tv_result.setText(String.valueOf(result));
    }
}
