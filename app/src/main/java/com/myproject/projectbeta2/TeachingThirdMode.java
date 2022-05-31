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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TeachingThirdMode extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "TAAAG";
    public static String[][] data = new String[30][2];
    public static int timeForDel = 300;
    public static WebView myWebView;
    static Handler handler;

    public TextView textView;
    public TextView textView2;
    public ImageButton button3;

    public static int mode;


    private static String word = "";
    public int randomRange = 10;
    private int number = 0;

    private DatabaseReference dataBase;

    private ImageButton button4;
    private ImageButton button5;
    private ImageButton button6;
    private ImageButton button7;

    @Override
    protected void onStop() {
        super.onStop();
        myWebView.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myWebView.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_third_mode);
        mode = TeachingFirstMode.mode;
        data[0][0] = null;
        myWebView = (WebView) findViewById(R.id.webview);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        button3 = (ImageButton) findViewById(R.id.imageButton2);
        button6 = (ImageButton) findViewById(R.id.imageButton6);
        button7 = (ImageButton) findViewById(R.id.imageButton7);

        button4 = (ImageButton) findViewById(R.id.imageButton4);
        button5 = (ImageButton) findViewById(R.id.imageButton5);

        button7.setOnClickListener(this);
        button6.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

        textView.setText("Loading...");
        textView2.setText("");

        button3.setOnClickListener(this);


        dataBase = FirebaseDatabase.getInstance().getReference("Mode");

        TeachingFirstMode.CreateDialog(this, "• Встаньте перед камерой и просигнальте предложенную букву \n• Для старта покажи символ 'Пробел' (обе руки вниз)");

        getDataFromDB();


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
                            String t;
                            if (data[msg.what][0].charAt(1) == ' ') {
                                t = data[msg.what][0].charAt(4) + "";
                            } else {
                                t = data[msg.what][0].charAt(5) + "";
                            }
                            if(t.charAt(0) == word.charAt(number)){
                                if(number < word.length()-1){
                                    textView.setText(textView.getText() + "" + word.charAt(number));
                                    textView2.setText("Next: " + word.charAt(number+1));
                                    number++;
                                }
                                else{
                                    textView.setText(word);
                                    textView2.setText("Отлично!");
                                    getDataFromDB();
                                    number = 0;
                                    first = false;
                                }
                            }
                        }
                        else if(msg.what == 0){
                            first = true;
                            textView.setText("");
                            textView2.setText("Next: " + (word.charAt(0)+""));
                        }
                    }
                    else{

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
        TeachingThirdMode.AnotherThread t = new TeachingThirdMode.AnotherThread();
        t.start();
    }

    public String RLC(String s) {
        return (s == null || s.length() == 0) ? "" : (s.substring(0, s.length() - 1));
    }

    public void getDataFromDB(){
        final Random random = new Random();
        ValueEventListener vListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Mode mode1 = ds.getValue(Mode.class);
                    if(mode1.name.charAt(0) == '1' && mode == 1){
                        word = mode1.words.get(random.nextInt(Math.toIntExact(mode1.number)));
                    }
                    if(mode1.name.charAt(0) == '0' && mode == 0){
                        word = mode1.words.get(random.nextInt(Math.toIntExact(mode1.number)));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dataBase.addValueEventListener(vListener);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.imageButton2:
                intent = new Intent(TeachingThirdMode.this, TeachingList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
            case R.id.imageButton4:
                intent = new Intent(TeachingThirdMode.this, TeachingFirstMode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
            case R.id.imageButton5:
                intent = new Intent(TeachingThirdMode.this, TeachingSecondMode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
            case R.id.imageButton6:
                break;
            case R.id.imageButton7:
                TeachingFirstMode.CreateDialog(this, "• Для стабильной работы вы должны полностью помещаться в кадр \n• Также обеспечьте хорошее освещение");
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
            while (true) {
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