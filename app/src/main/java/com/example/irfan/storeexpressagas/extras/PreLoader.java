package com.example.irfan.storeexpressagas.extras;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.irfan.storeexpressagas.R;

public class PreLoader {

    public static ProgressDialog mDialog;

    public static void setPreLoader(String loadingMessage,boolean cancelable, Context context){
        if(mDialog != null){

            mDialog=null;
//    mDialog.dismiss();
        }
        mDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        mDialog.setMessage(loadingMessage);
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(false);



    }
    public static void show(){

        mDialog.show();

    }

    public static void hide(){

        mDialog.hide();

    }

    public static void setMessage(String message){

        mDialog.setMessage(message);

    }

    public static void dismiss(){

        mDialog.dismiss();

    }
}
