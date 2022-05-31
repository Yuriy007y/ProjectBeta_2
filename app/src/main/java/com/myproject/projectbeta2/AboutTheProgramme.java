package com.myproject.projectbeta2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AboutTheProgramme extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_programme);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.imageButton2:
                intent = new Intent(AboutTheProgramme.this, FirstScreenActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);

                break;
        }
    }
}