package com.myproject.projectbeta2;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context mContext;
    public String data;
    private int Letters;


    WebAppInterface(Context ctx){
        this.mContext=ctx;
    }

    @JavascriptInterface
    public void sendData(String ClassName, String Value, int i) {
            Translator1.data[i][0] = ClassName;
            Translator1.data[i][1] = Value;

            Teaching3.data[i][0] = ClassName;
            Teaching3.data[i][1] = Value;

    }
}
