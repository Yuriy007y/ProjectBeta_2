package com.myproject.projectbeta2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class TranslatorFM extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "TAAAG";
    public static String[][] data = new String[30][2];
    public static boolean p1 = false;
    public static int timeForDel = 300;
    public static WebView myWebView;
    static Handler handler;

    public TextView textView;
    public Button button;
    public ImageButton button2;
    public ImageButton button3;

    private boolean enabled = true;

    AnotherThread t;


    @Override
    protected void onStop() {
        super.onStop();
        myWebView.destroy();
        enabled = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myWebView.destroy();
        enabled = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_fm);
        data[0][0] = null;
        myWebView = (WebView) findViewById(R.id.webview);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button2);
        button2 = (ImageButton) findViewById(R.id.imageButton8);
        button3 = (ImageButton) findViewById(R.id.imageButton);
        textView.setText("Loading...");

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        TeachingFirstMode.CreateDialog(this, "• Для старта покажи символ 'Пробел' (обе руки вниз) \n• Для стабильной работы вы должны полностью помещаться в кадр");



        handler = new Handler() {
            public boolean first = false;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(data[0][0]!=null){
                    if(textView.getText() == "Loading..."){
                        textView.setText("Ready!");
                    }
                    else if(msg.what < data.length){
                        if(first) {
                            if(p1){
                                data[0][0] = "0 - _Idle";
                            }
                            if (data[msg.what][0].charAt(1) == ' ') {
                                textView.setText(textView.getText() + (data[msg.what][0].charAt(4) + ""));
                            } else {
                                textView.setText(textView.getText() + (data[msg.what][0].charAt(5) + ""));
                            }
                        }
                        else if(msg.what == 0){
                            first = true;
                            textView.setText("");
                        }
                    }
                    else{
                        if(msg.what == 400 && textView.getText() != "Ready!"){
                            String temp = (String) textView.getText();
                            textView.setText(RLC(temp));
                        }
                        if(msg.what == 401){
                            first = false;
                        }
                    }
                }
            }
        };


        WebSettings webSettings = myWebView.getSettings();
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        CameraManager cameraManager = (CameraManager) this.getApplicationContext().getSystemService(Context.CAMERA_SERVICE);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);

        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.loadUrl("file:///android_asset/index.html");

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }
        });
        t = new AnotherThread();
        t.start();
    }

    public String RLC(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button2:
                if(textView.getText() != "Loading..."){
                    textView.setText("Ready!");
                    handler.sendEmptyMessage(401);
                }
                break;
            case R.id.imageButton:
                intent = new Intent(TranslatorFM.this, TranslatorSM.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slidein, R.anim.slideout);

                break;
            case R.id.imageButton8:
                intent = new Intent(TranslatorFM.this, FirstScreenActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);

                break;
        }
    }


    class AnotherThread extends Thread {
        @Override
        public void run() {
            int last = 0;
            int now = -1;
            int count = 0;
            while (data[0][0] == null) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            handler.sendEmptyMessage(1);
            while (enabled) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                last = now;
                now = LetterRec(data);
                if (last == now) {
                    if (dataValue(data, now)) {
                        count++;
                        if (count == 95) {
                            handler.sendEmptyMessage(now);
                        }
                        else if(count == timeForDel){
                            handler.sendEmptyMessage(400);
                        }
                    }
                } else if (count >= 20) {
                    count = 0;
                }
            }
        }

        public int LetterRec(String[][] a){
            int max = 0;
            for(int i = 0; i < a.length; i++){
                if(a[i][0] != null) {
                    if (Float.parseFloat(a[i][1]) > Float.parseFloat(a[max][1])) {
                        max = i;
                    }
                }
            }
            return max;
        }

        public boolean dataValue(String[][] a, int n) {
            if (a[n][0] != null) {
                if (Float.parseFloat(a[n][1]) > 0.83f) {
                    return true;
                }
            }
            return false;
        }
    }
}