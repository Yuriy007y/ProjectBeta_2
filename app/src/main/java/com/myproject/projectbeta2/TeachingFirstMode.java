package com.myproject.projectbeta2;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class TeachingFirstMode extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private ImageView imageView;
    private TextView textView;
    private ImageButton button;

    private ImageButton button3;
    private ImageButton button4;
    private ImageButton button5;

    private ListView listView;

    public static int mode;

    private DatabaseReference dataBase;
    private static String word = "";
    private String lastWord;

    private boolean started = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_first_mode);

        button = (ImageButton) findViewById(R.id.imageButton2);

        button3 = (ImageButton) findViewById(R.id.imageButton4);
        button4 = (ImageButton) findViewById(R.id.imageButton5);
        button5 = (ImageButton) findViewById(R.id.imageButton6);

        editText = (EditText) findViewById(R.id.editText);

        listView = (ListView) findViewById(R.id.List);

        button5.setOnClickListener(this);
        button4.setOnClickListener(this);
        button3.setOnClickListener(this);
        button.setOnClickListener(this);


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (mode) {
            case 0:
                TeachingFMImageFragment newFragment1 = new TeachingFMImageFragment();
                ft.replace(R.id.container, newFragment1);
                break;
            case 1:
                TeachingSMListFragment newFragment2 = new TeachingSMListFragment();
                ft.replace(R.id.container, newFragment2);
                break;
        }

        ft.addToBackStack(null);
        ft.commit();



    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imageButton2:
                intent = new Intent(TeachingFirstMode.this, TeachingList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
            case R.id.imageButton4:
                break;
            case R.id.imageButton5:
                intent = new Intent(TeachingFirstMode.this, TeachingSecondMode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
                break;
            case R.id.imageButton6:
                intent = new Intent(TeachingFirstMode.this, TeachingThirdMode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
                break;
        }
    }

    public static void CreateDialog(Activity activity, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Информация")
                .setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }

}