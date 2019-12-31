package com.example.irfan.storeexpressagas.activities;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.databinding.ActivitySplashBinding;
import com.example.irfan.storeexpressagas.extras.PrefManager;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends BaseActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    ActivitySplashBinding binding;

    public PrefManager sharedperference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(SplashScreen.this,R.layout.activity_splash);
        parentBinding=binding;
        sharedperference=new PrefManager(this);

        openNewScreen();

    }

    private void openNewScreen() {

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {


                Intent i;

                if(sharedperference.getToken()==null || sharedperference.getToken()=="") {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                }
                else{
                    i = new Intent(SplashScreen.this, MainActivity.class);

                }

                //i = new Intent(SplashScreen.this, LanuageSelection.class);


                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

}
