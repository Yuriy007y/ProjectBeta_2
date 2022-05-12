package com.myproject.projectbeta2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class TeachingList extends AppCompatActivity implements View.OnClickListener {
    String[] modes = {"Буквы", "Слова"};
    private ImageButton button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_list);

        button1 = (ImageButton) findViewById(R.id.imageButton2);
        ListView teachList = findViewById(R.id.List);

        button1.setOnClickListener(this);

        teachList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                switch (name){
                    case "Буквы":
                        Teaching1.mode = 0;
                        break;
                    case "Слова":
                        Teaching1.mode = 1;
                        break;
                }
                Intent intent;
                intent = new Intent(TeachingList.this, Teaching1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, modes);

        teachList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.imageButton2:
                intent = new Intent(TeachingList.this, Screen1Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideout2, R.anim.slidein2);
                break;
        }
    }
}