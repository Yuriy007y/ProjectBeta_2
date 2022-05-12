package com.myproject.projectbeta2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;

public class Translator2 extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ListView listView;
    private EditText editText;

    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator2);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton11);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton12);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.List);
        editText = (EditText) findViewById(R.id.editText);

        MyLetterAdapter myLetterAdapter = new MyLetterAdapter(Translator2.this, makeLetters("Привет"));
        listView.setAdapter(myLetterAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MyLetterAdapter myLetterAdapter = new MyLetterAdapter(Translator2.this, makeLetters(String.valueOf(editText.getText())));
                listView.setAdapter(myLetterAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.imageButton11:
                intent = new Intent(Translator2.this, Translator1.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);

                break;
            case R.id.imageButton12:
                intent = new Intent(Translator2.this, Screen1Activity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);

                break;
        }
    }
    public static MyLetter[] makeLetters(String word) {
        MyLetter[] arr = new MyLetter[word.length()];
        word = word.toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            MyLetter letter = new MyLetter();
            if(word.charAt(i) != ' '){
                letter.letter = String.valueOf(word.charAt(i));
            }
            else{
                letter.letter = "Пробел";
            }
            String nowLetter = String.valueOf(word.charAt(i));

            switch (nowLetter){
                case " ":
                    nowLetter = "Пробел";
                    break;
                case "Ё":
                    nowLetter = "Е";
                    break;
                case "Э":
                    nowLetter = "Е";
                    break;
                case "Ъ":
                    nowLetter = "Ь";
                    break;
                case "Й":
                    nowLetter = "И";
                    break;
            }

            letter.image = nowLetter;

            arr[i] = letter;
        }
        return arr;
    }
}