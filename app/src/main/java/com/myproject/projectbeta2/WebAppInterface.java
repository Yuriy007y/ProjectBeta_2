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
            TranslatorFM.data[i][0] = ClassName;
            TranslatorFM.data[i][1] = Value;

            TeachingThirdMode.data[i][0] = ClassName;
            TeachingThirdMode.data[i][1] = Value;

    }
}
