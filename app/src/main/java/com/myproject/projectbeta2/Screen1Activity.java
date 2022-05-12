package com.myproject.projectbeta2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Screen1Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button4 = (Button) findViewById(R.id.button4);
        ImageView cloud1 = (ImageView) findViewById(R.id.cloud1);
        ImageView cloud2 = (ImageView) findViewById(R.id.cloud2);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button4.setOnClickListener(this);

        cloud1.setOnClickListener(this);
        cloud2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button:
                intent = new Intent(Screen1Activity.this, Translator1.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slidein, R.anim.slideout);

                break;
            case R.id.button2:
                intent = new Intent(Screen1Activity.this, AboutTheProgramme.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
                break;
            case R.id.button4:
                if(isOnline(Screen1Activity.this)){
                    intent = new Intent(Screen1Activity.this, TeachingList.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slidein, R.anim.slideout);
                }
                else{
                    Teaching1.CreateDialog(Screen1Activity.this, "No internet connection.");
                }
                break;
            case R.id.cloud2:
                Teaching1.CreateDialog(this, "Артековец сегодня - артековец всегда!");
                break;
            case R.id.cloud1:
                Teaching1.CreateDialog(this, "Семафорную азбуку разработал в 1895 году вице-адмирал Степан Осипович Макаров.");
                break;
        }
    }
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}