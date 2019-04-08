package com.ebrightmoon.jni;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ebrightmoon.jni.crypto.Crypto;
import com.ebrightmoon.jni.encrypt.AESCrypt;

public class MainActivity extends AppCompatActivity {
    private Crypto crypto;
    private static final int REQUEST_READ_PHONE_STATE = 1000;
    private String encode;
    public static final String serverPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALXwJ4I+ZJUfswcy3eeuMhQVSm/WzfIYDlYv3ni5vm1Iv06V2/49rwirPGUoT3k/rfhzx1MmMWv31UIz9yozN+7CcDDdz76wuc9kIJ4AR830ELZMTzqoHWPSAqaFKDeGKXX9PDpyPswFLCX1WtHHm6K/M+ntP+6J6Pi6mqt9P3epAgMBAAECgYAtgNXwzjgLz/TPvRog4sFlonmOhTPW88tKJQjIOvR0krg+KF7wNG89hM5DIpTV52ZUeGiG1EuSDFcLCsIrjMnVAjEQtfEzD2fEW9VUxucxdJDWyDNK6qxji+NO1vy6U779K675rFvJxmsDxNvKph1ht0gYlDn5RyFd4nrOrfgjTQJBAOPrQ4NmdWFS94NYkJ4fIseZPfRSm9NzqHlFp4Pr44nUrLKYeGVCIjnfsGAWuZ7sxLSkSSLN0mS/Rk7PH+7uv1cCQQDMWp5fBSRBCRsTB8v4YFumIPxpYms6cWChjZ1GvnR+4T5DJ/4pE768LvQP8G9Hh18JzHaxwplTDhlhJfEIHCD/AkEAiTMHOiNER6jk/DklHTpK+nJB/EB6MyitYwtOEri+CStwJjZoSzQrXEFOcBld9dA7fS5kJEJYA3OtBCXk6DTqEwJAemftZU1XIf2qUgPhka1mOGSZzSY+xIsVLq/8/VsnvLh+6wsRmtlQ7rfRSZrjjRzxOJVYo7HE1ZMkcKShdBIlUQJAd3a+zP2rqh/rJu8izyE5KGGJLMlV1wQKOPFpNAGi+90VpkW3wETvhk7RL1KIxv5jVIYqfu4vrxEkpzvY75ZKJA==";
    public static final String serverPublicKey = "BwIAAACkAABSU0EyAAQAAAEAAQBZF5P+EkFcZUOtwLZUf7Yux/HwKNONLS9xXkSxMGZr1EEA8jDS/QOTCxl3T31A9NttBhBqbYlhxrRhWOTeaBijQpAmoU44Ol3JW9Grq31W5xPe53ZjT5U1B4pXyqkYUVofIoz51jGaPNsX77zoEodQ8T2RBTCdziqv80hqRnD12YFF8oU+YwjUop4Fdpb1T8angHcUcr3CLjDZc/u7AZUFsL9aqL0C9cwRLZtljjmCPwiVJP9w8WryX3dBQTIoF/3ZrT7mcN20q39msZSaOWKt0yNWDDNhUbsi7gDlAAVjsPluql5/nu2c19H9r0fXLP+SIo97mAtbSD/3lwql43bcgaK8GA5lsQdh5PNJcremDzmCcVH539idEVdkr66q2n500Xm+2vDYC44F2RNFFf8kkeMMUXzXF4A9mwe5rpneh8E/pXuGRuSxm77rw3zVhHUhFrJqjy27goZl9zPyX1Sfl51FsyhO9T5DZLnGpF3Cn3T/wmt6NZOqOVctkVN8sYYZTJmwzW+PW8YiaJZRiaEj6RFRf/Kkybx2oxj3w3jGr72UKKihp/kNP1q/+EHtt81krynPMK07w+Nx/B5DyrMpAYCGKEH5M1geyID5NYbOybZV/9g1orh8m4S4924tzbY7OHchweZGf6N19E6lyni8vZUmdh97iCI06eCgoD2p7qZ17mIZ18pPynmPJfcAuxGvMlBL+d5GKanAeddlU5DCx8vk47bIpYzKBLvmzdV8pjwLmYIp7Hp6PukZNgX+WLo=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crypto = new Crypto();
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
//        encode = crypto.encrypt("21044321");
//        tv.setText(encode);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
//TODO
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, crypto.decode(encode,3), Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this, crypto.stringFromJNI(MainActivity.this), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        try {
            String cleartext="15300086234";
//            Map<String, Object> stringObjectMap = RSAHelper.initKey();
//            System.out.println("私钥======================"+RSAHelper.getPrivateKey(stringObjectMap));
//            System.out.println("公钥======================"+RSAHelper.getPublicKey(stringObjectMap));
//            String pwd=RSAHelper.encryptByPublicKey(cleartext,RSAHelper.getPublicKey(stringObjectMap));
//            String passwd="qdrCx60LRnkYGuz7F9XuNYhovOHW5rvGTkh/74c4BzYl5++RykDQjQJB0mPqxMeXmQQ55kKrYJiDXi1nVEpLfi+PiPzKdOA1VYekZ84jn7u5b4cXYknvSyJJvpriILgjwg+Lcl49d5QMwAAJHyPov7+0i3joeqIlahVNKXb4be8=";
//            System.out.println("加密 =================="+pwd);
//            System.out.println("解密 =================="+ RSAHelper.decryptByPrivateKey(pwd,RSAHelper.getPrivateKey(stringObjectMap)));
//            System.out.println("=================="+ RSAHelper.decryptByPrivateKey(passwd,serverPrivateKey));
//            System.out.println("=================="+ DESUtils.encrypt("15300086234","bihupicc"));
//            System.out.println("=================="+DESUtils.decrypt(DESUtils.encrypt("15300086234","bihupicc"),"bihupicc"));
            String pass= AESCrypt.encrypt("bihupicc","15300086234");
//            System.out.println("================="+ Base64.encodeBase64("15300086234".getBytes()));
            System.out.println("加密=================="+ pass);
            System.out.println("解密=================="+AESCrypt.decrypt("bihupicc",pass));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
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
}
