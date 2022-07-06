package com.ebrightmoon.jni;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ebrightmoon.jni.crypto.Crypto;
import com.ebrightmoon.jni.crypto.JNITool;
import com.ebrightmoon.jni.encrypt.AESCrypt;
import com.ebrightmoon.jni.encrypt.DESUtils;
import com.ebrightmoon.jni.encrypt.RSAHelper;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * 加密解密测试
 */
public class EncryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EncryActivity.class.getSimpleName();
    private static final int REQUEST_READ_PHONE_STATE = 1000;
    public static final String serverPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmwAFOdmhrnvqKwtQowPYJaSzsa58BqE6mX2zySS15Ddh6yhUjm29IjcTkOIRtbzVbmXY9DxQeBdc3i6tiM+5k4EV3MHJs2a7fazOYW/MsKIqtI/BZl77hLlDMriUuVen7ixTyiQqeCpPkcxAP1eePH8k4v5vZrmN2Rr7L7xp9RwIDAQAB";
    public static final String serverPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAObAAU52aGue+orC1CjA9glpLOxrnwGoTqZfbPJJLXkN2HrKFSObb0iNxOQ4hG1vNVuZdj0PFB4F1zeLq2Iz7mTgRXcwcmzZrt9rM5hb8ywoiq0j8FmXvuEuUMyuJS5V6fuLFPKJCp4Kk+RzEA/V548fyTi/m9muY3ZGvsvvGn1HAgMBAAECgYEAsih82b/CT5Wni4txkyWo0QtLjB5r3jO8xefXjhnB6j0ub1+ngp54BWSwKCUa2gOLBvYtvGbv+V45FMHZyTCfVfwgu6k6sGEF+cQIXWuW7WAbAobUkLBUeQr8vFXChwdFdd7mQ06J1eOVppjG6N6V0XpHQPBcIPtAgoKJw+VEPEECQQD0uRut90fgYg+MXTsnB7lgInHPXmfpMZmpvAOAxNkOOyq/gTg3IPjITV2br4GEWYYzckFszj9hRqhp+Q1SvIZbAkEA8WIQGJvMjyTuQaozAzH9fkGbH6y9kQ96b/NWVsmKxyd75ga+oF3IzvI/iJYYWUBnofG5POGCYlBCITFfqbkQhQJAQaJ36wOcUnDabLIAkGpA7KiwT4apZeC4rs5PPjUNZgS2ZWBZ3GdKciZVydCbcwyzso6dP3pdg1B5ENsMGLmZawJBAN+rtq6W795UolJGnC6BzBuKP2wCbUZVyWajXYXeC7Kva6ei6FFBlintX+H482cAvwbZLoSzklX3eM+5KKPk0OECQQC2JMTxv3F7iULuxxVImjxf/QYSu6/FWxPMcaZC6ShQhjLjkZd231RalQhOO6QNsKEKq3zp7dMTeUA5h+D6ig5R";
    private EditText et_decode_text;
    private EditText et_encode_text;
    private Button btn_encode;
    private Button btn_decode;
    private RadioGroup rg_type;
    private RadioButton rb_aes;
    private RadioButton rb_des;
    private RadioButton rb_rsa;
    private String password = "123456dasdsadas";
    private RadioButton rb_cus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        Log.e(TAG, "signature：" + JNITool.getSignature(this));
        String originalStr = "123";
        JNITool jniTool = new JNITool();
        String encrypt = JNITool.encrypt(originalStr, 0);
        Log.e(TAG, "原字符串：" + originalStr);
        Log.e(TAG, "AES ECB PKCS7Padding 加密：" + encrypt);
        Log.e(TAG, "AES ECB PKCS7Padding 解密：" + JNITool.decrypt(encrypt, 0));
        Log.e(TAG, "获取唯一值：" + jniTool.getUUID(this));

        String pwdStr = "pwd123456";
        Log.e(TAG, "password: " + pwdStr);
        Log.e(TAG, "md5再加salt: " + JNITool.pwdMD5(pwdStr));
        try {
            Map<String, Object> stringObjectMap = RSAHelper.initKey();
            Log.e(TAG, "公钥： " + RSAHelper.getPublicKey(stringObjectMap));
            Log.e(TAG, "私钥: " + RSAHelper.getPrivateKey(stringObjectMap));
        } catch (Exception e) {
            e.printStackTrace();
        }


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {

        }


    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化View
     */
    private void initView() {
        et_decode_text = findViewById(R.id.et_decode_text);
        et_encode_text = findViewById(R.id.et_encode_text);

        btn_encode = findViewById(R.id.btn_encode);
        btn_decode = findViewById(R.id.btn_decode);
        btn_decode.setOnClickListener(this);
        btn_encode.setOnClickListener(this);

        rg_type = findViewById(R.id.rg_type);
        rb_aes = findViewById(R.id.rb_aes);
        rb_des = findViewById(R.id.rb_des);
        rb_rsa = findViewById(R.id.rb_rsa);
        rb_cus = findViewById(R.id.rb_cus);
        rg_type.check(R.id.rb_aes);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //解密
            case R.id.btn_decode:
                if (TextUtils.isEmpty(et_encode_text.getText().toString().trim())) {
                    Toast.makeText(this, "密文为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                decodeData(et_encode_text.getText().toString().trim());
                break;
            //加密
            case R.id.btn_encode:
                if (TextUtils.isEmpty(et_decode_text.getText().toString().trim())) {
                    Toast.makeText(this, "内容为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                encodeData(et_decode_text.getText().toString().trim());
                break;
        }
    }

    /**
     * 加密数据
     */
    private void encodeData(String text) {
//        String password=crypto.encode(text, 3);
        String encodeData = null;

        switch (rg_type.getCheckedRadioButtonId()) {
            case R.id.rb_aes:
                try {
                    encodeData = AESCrypt.encrypt(password, text);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_des:
                try {
                    encodeData = DESUtils.encode(text, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_rsa:
                try {
                    encodeData = RSAHelper.encryptByPublicKey(text, serverPublicKey);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_cus:
                try {
                    encodeData = Crypto.encrypt(text, 18);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

        et_encode_text.setText(encodeData);
    }

    /**
     * 解密数据
     */
    private void decodeData(String text) {

        String laws = null;
        switch (rg_type.getCheckedRadioButtonId()) {
            case R.id.rb_aes:
                try {
                    laws = AESCrypt.decrypt(password, text);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_des:
                try {
                    laws = DESUtils.decode(text, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_rsa:
                try {
                    laws = RSAHelper.decryptByPrivateKey(text, serverPrivateKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_cus:
                try {
                    laws = Crypto.decrypt(text, 18);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

        et_decode_text.setText(laws);

    }
}
