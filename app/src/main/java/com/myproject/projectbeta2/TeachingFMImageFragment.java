package com.myproject.projectbeta2;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class TeachingFMImageFragment extends Fragment {
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teaching_fm_image_fragment, container, false);
        TeachingSecondMode.LoadPicture(((ImageView)view.findViewById(R.id.imageView3)), "Пробел");
        ((EditText)view.findViewById(R.id.editText2)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText editText = (EditText) view.findViewById(R.id.editText2);
                if (editText.getText().length() > 0) {
                    TeachingSecondMode.LoadPicture(((ImageView) view.findViewById(R.id.imageView3)), String.valueOf(editText.getText().charAt(editText.getText().length()-1)).toUpperCase());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}