package com.ebrightmoon.jni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

/**
 * Time: 2019-11-25
 * Author:wyy
 * Description:
 */
public class HomeActivity extends AppCompatActivity {

    private AppCompatButton btn_calculator;
    private AppCompatButton btn_encryption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        btn_calculator = findViewById(R.id.btn_calculator);
        btn_encryption = findViewById(R.id.btn_encryption);

        btn_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CalculatorActivity.class));
            }
        });

        btn_encryption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,EncryActivity.class));

            }
        });
    }


}
