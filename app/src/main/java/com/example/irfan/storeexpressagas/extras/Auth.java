package com.example.irfan.storeexpressagas.extras;

import android.content.Context;

public class Auth {
    public static PrefManager sharedperference;
    public static String Token="Bearer ";

    public static String getToken(Context context){

        sharedperference=new PrefManager(context);
        return sharedperference.getToken();

    }


}
