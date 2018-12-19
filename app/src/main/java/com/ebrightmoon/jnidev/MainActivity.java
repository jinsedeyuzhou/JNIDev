package com.ebrightmoon.jnidev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ebrightmoon.jnidev.crypto.Crypto;

public class MainActivity extends AppCompatActivity {
    private Crypto crypto;

    // Used to load the 'native-lib' library on application startup.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crypto = new Crypto();
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, crypto.decrypt("adfdfadfsfds"),Toast.LENGTH_LONG).show();
            }
        });
    }


}
