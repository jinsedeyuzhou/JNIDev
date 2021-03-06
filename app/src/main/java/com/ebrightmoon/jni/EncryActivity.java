package com.ebrightmoon.jni;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ebrightmoon.jni.crypto.Crypto;
import com.ebrightmoon.jni.crypto.JNITool;
import com.ebrightmoon.jni.encrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * 加密解密测试
 */
public class EncryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG =EncryActivity.class.getSimpleName() ;
    private Crypto crypto;
    private static final int REQUEST_READ_PHONE_STATE = 1000;
    public static final String serverPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALXwJ4I+ZJUfswcy3eeuMhQVSm/WzfIYDlYv3ni5vm1Iv06V2/49rwirPGUoT3k/rfhzx1MmMWv31UIz9yozN+7CcDDdz76wuc9kIJ4AR830ELZMTzqoHWPSAqaFKDeGKXX9PDpyPswFLCX1WtHHm6K/M+ntP+6J6Pi6mqt9P3epAgMBAAECgYAtgNXwzjgLz/TPvRog4sFlonmOhTPW88tKJQjIOvR0krg+KF7wNG89hM5DIpTV52ZUeGiG1EuSDFcLCsIrjMnVAjEQtfEzD2fEW9VUxucxdJDWyDNK6qxji+NO1vy6U779K675rFvJxmsDxNvKph1ht0gYlDn5RyFd4nrOrfgjTQJBAOPrQ4NmdWFS94NYkJ4fIseZPfRSm9NzqHlFp4Pr44nUrLKYeGVCIjnfsGAWuZ7sxLSkSSLN0mS/Rk7PH+7uv1cCQQDMWp5fBSRBCRsTB8v4YFumIPxpYms6cWChjZ1GvnR+4T5DJ/4pE768LvQP8G9Hh18JzHaxwplTDhlhJfEIHCD/AkEAiTMHOiNER6jk/DklHTpK+nJB/EB6MyitYwtOEri+CStwJjZoSzQrXEFOcBld9dA7fS5kJEJYA3OtBCXk6DTqEwJAemftZU1XIf2qUgPhka1mOGSZzSY+xIsVLq/8/VsnvLh+6wsRmtlQ7rfRSZrjjRzxOJVYo7HE1ZMkcKShdBIlUQJAd3a+zP2rqh/rJu8izyE5KGGJLMlV1wQKOPFpNAGi+90VpkW3wETvhk7RL1KIxv5jVIYqfu4vrxEkpzvY75ZKJA==";
    public static final String serverPublicKey = "BwIAAACkAABSU0EyAAQAAAEAAQBZF5P+EkFcZUOtwLZUf7Yux/HwKNONLS9xXkSxMGZr1EEA8jDS/QOTCxl3T31A9NttBhBqbYlhxrRhWOTeaBijQpAmoU44Ol3JW9Grq31W5xPe53ZjT5U1B4pXyqkYUVofIoz51jGaPNsX77zoEodQ8T2RBTCdziqv80hqRnD12YFF8oU+YwjUop4Fdpb1T8angHcUcr3CLjDZc/u7AZUFsL9aqL0C9cwRLZtljjmCPwiVJP9w8WryX3dBQTIoF/3ZrT7mcN20q39msZSaOWKt0yNWDDNhUbsi7gDlAAVjsPluql5/nu2c19H9r0fXLP+SIo97mAtbSD/3lwql43bcgaK8GA5lsQdh5PNJcremDzmCcVH539idEVdkr66q2n500Xm+2vDYC44F2RNFFf8kkeMMUXzXF4A9mwe5rpneh8E/pXuGRuSxm77rw3zVhHUhFrJqjy27goZl9zPyX1Sfl51FsyhO9T5DZLnGpF3Cn3T/wmt6NZOqOVctkVN8sYYZTJmwzW+PW8YiaJZRiaEj6RFRf/Kkybx2oxj3w3jGr72UKKihp/kNP1q/+EHtt81krynPMK07w+Nx/B5DyrMpAYCGKEH5M1geyID5NYbOybZV/9g1orh8m4S4924tzbY7OHchweZGf6N19E6lyni8vZUmdh97iCI06eCgoD2p7qZ17mIZ18pPynmPJfcAuxGvMlBL+d5GKanAeddlU5DCx8vk47bIpYzKBLvmzdV8pjwLmYIp7Hp6PukZNgX+WLo=";
    private EditText et_decode_text;
    private EditText et_encode_text;
    private Button btn_encode;
    private Button btn_decode;
    private RadioGroup rg_type;
    private RadioButton rb_aes;
    private RadioButton rb_des;
    private RadioButton rb_rsa;
    private String password = "123456";
    private RadioButton rb_cus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        Log.e(TAG, "signature：" + JNITool.getSignature(this));
        String originalStr = "123";
        JNITool jniTool=new JNITool();
        String encrypt = JNITool.encrypt(originalStr);
        Log.e(TAG, "原字符串：" + originalStr);
        Log.e(TAG, "AES ECB PKCS7Padding 加密：" + encrypt);
        Log.e(TAG, "AES ECB PKCS7Padding 解密：" + JNITool.decrypt(encrypt));
        Log.e(TAG, "stringFromJNI：" + jniTool.stringFromJNI(this));

        String pwdStr = "pwd123456";
        Log.e(TAG, "password: " + pwdStr);
        Log.e(TAG, "md5再加salt: " + JNITool.pwdMD5(pwdStr));
        crypto = new Crypto();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
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

                break;
            case R.id.rb_rsa:

                break;
            case R.id.rb_cus:
                encodeData = crypto.encrypt(text, 18);
                break;

        }

        et_encode_text.setText(encodeData);
    }

    /**
     * 解密数据
     */
    private void decodeData(String text) {

//        String laws=crypto.decode(text, 3);
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

                break;
            case R.id.rb_rsa:

                break;
            case R.id.rb_cus:
                laws = crypto.decrypt(text, 18);
                break;

        }

        et_decode_text.setText(laws);

    }
}
