package com.myproject.projectbeta2;

import static com.myproject.projectbeta2.TranslatorSM.makeLetters;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

public class TeachingSMListFragment extends Fragment {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teaching_fm_list_fragment, container, false);

        EditText editText = (EditText) view.findViewById(R.id.editText3);
        ListView listView = (ListView) view.findViewById(R.id.List);

        MyLetterAdapter myLetterAdapter = new MyLetterAdapter(getContext(), makeLetters("Привет"));
        listView.setAdapter(myLetterAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MyLetterAdapter myLetterAdapter = new MyLetterAdapter(getContext(), makeLetters(String.valueOf(editText.getText())));
                listView.setAdapter(myLetterAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}