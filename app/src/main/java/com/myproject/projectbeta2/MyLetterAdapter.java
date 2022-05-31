package com.myproject.projectbeta2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyLetterAdapter extends ArrayAdapter<MyLetter> {

    public MyLetterAdapter(Context context, MyLetter[] arr) {
        super(context, R.layout.list_item, arr);
    }

    ImageView imageView;
    TextView textView;
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyLetter letter = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }
        imageView = (ImageView) convertView.findViewById(R.id.image_view_icon);
        textView = (TextView) convertView.findViewById(R.id.text_view_name);
        textView.setText(" - " + letter.letter);
        TeachingSecondMode.LoadPicture(imageView, letter.image);
        return convertView;
    }
}
