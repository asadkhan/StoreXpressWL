package com.example.irfan.storeexpressagas.extras;

import android.content.Context;
import android.content.Intent;

public class ActivityManager {

    public static void startActivity(Context context,Class activity) {

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }



}
