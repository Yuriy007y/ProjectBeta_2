package com.myproject.projectbeta2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TeachingSecondMode extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private ImageView imageView;
    private TextView textView;
    private ImageButton button;
    private Button button2;

    private ImageButton button3;
    private ImageButton button4;
    private ImageButton button5;

    public static int mode;

    private DatabaseReference dataBase;
    private static String word = "";
    private String lastWord;

    private boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_second_mode);
        mode = TeachingFirstMode.mode;
        imageView = (ImageView) findViewById(R.id.imageView2);
        editText = (EditText) findViewById(R.id.editTextTextPersonName);
        textView = (TextView) findViewById(R.id.textView7);
        button = (ImageButton) findViewById(R.id.imageButton2);
        button2 = (Button) findViewById(R.id.button5);
        button5 = (ImageButton) findViewById(R.id.imageButton6);

        button3 = (ImageButton) findViewById(R.id.imageButton4);
        button4 = (ImageButton) findViewById(R.id.imageButton5);

        button5.setOnClickListener(this);
        button4.setOnClickListener(this);
        button3.setOnClickListener(this);
        button2.setOnClickListener(this);
        button.setOnClickListener(this);

        getDataFromDB();

        TeachingFirstMode.CreateDialog(this, "• Вводите букву, которую показывает сигнальщик.");

        button2.setText("Старт");
        LoadPicture(imageView, "Пробел");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!started){ }
                else if (editText.getText().length() < word.length()) {
                    LoadPicture(imageView, String.valueOf(word.charAt(editText.getText().length())));
                    textView.setText("");
                } else if (editText.getText().toString().toUpperCase().equals(word)) {
                    textView.setText("Отлично!");
                    textView.setTextColor(Color.GREEN);
                    lastWord = word;
                    getDataFromDB();
                } else {
                    textView.setText("Ошибка");
                    textView.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getDataFromDB() {
        dataBase = FirebaseDatabase.getInstance().getReference("Mode");
        final Random random = new Random();
        ValueEventListener vListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Mode mode1 = ds.getValue(Mode.class);
                    if (mode1.name.charAt(0) == '1' && mode == 1) {
                        word = mode1.words.get(random.nextInt(Math.toIntExact(mode1.number)));
                    }
                    if (mode1.name.charAt(0) == '0' && mode == 0) {
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
        switch (v.getId()) {
            case R.id.imageButton2:
                intent = new Intent(TeachingSecondMode.this, TeachingList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
            case R.id.button5:
                if(!started && word != "") {
                    started = true;
                    button2.setText("Далее");
                    editText.setHint("Введите букву");
                    editText.setText("");
                    textView.setText("");
                    try {
                        LoadPicture(imageView, String.valueOf(word.charAt(0)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(word == ""){
                    Toast.makeText(TeachingSecondMode.this, "Please wait...", Toast.LENGTH_SHORT).show();
                }
                if (editText.getText().toString().toUpperCase().equals(lastWord)) {
                    editText.setText("");
                    textView.setText("");
                    LoadPicture(imageView, String.valueOf(word.charAt(0)));
                }
                break;
            case R.id.imageButton4:
                intent = new Intent(TeachingSecondMode.this, TeachingFirstMode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
            case R.id.imageButton6:
                intent = new Intent(TeachingSecondMode.this, TeachingThirdMode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
                break;
            case R.id.imageButton5:
                break;
        }
    }

    public static void LoadPicture(ImageView imageView, String letter){
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/samsung-project-90df5.appspot.com/o/" + letter + ".jpg?alt=media&token=dbb79688-8244-4136-a705-87b2f77881d7").fit().into(imageView);
    }
}