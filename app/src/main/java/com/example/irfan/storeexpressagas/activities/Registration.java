package com.example.irfan.storeexpressagas.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.ValidationUtility;
import com.example.irfan.storeexpressagas.models.GResponse;
import com.example.irfan.storeexpressagas.models.LoginResponse;
import com.example.irfan.storeexpressagas.models.RegistrationRequest;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

public class Registration extends BaseActivity implements View.OnClickListener  {

Button Btn_Signin,Btn_Submit;
EditText Txt_name,Txt_phone,Txt_email,Txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Btn_Signin = (Button) findViewById(R.id.btn_signin);
        Btn_Submit = (Button) findViewById(R.id.btn_submit);
        Txt_name = (EditText) findViewById(R.id.Txt_name);
        Txt_email = (EditText) findViewById(R.id.Txt_email);
        Txt_password = (EditText) findViewById(R.id.Txt_password);
        Txt_phone = (EditText) findViewById(R.id.Txt_phonenumber);


        setEventListners();


    }



    private void setEventListners() {
        Btn_Signin.setOnClickListener(this);
        Btn_Submit.setOnClickListener(this);


    }






    @Override
    public void onClick(View v) {
        Log.d("test","click");
        switch (v.getId()) {
            case R.id.btn_signin:

                openActivity(Login.class);

                break;


            case R.id.btn_submit:
                Log.d("test","show msg call");
              //  showMessageDailogNextScreen("test","testing message",Login.class);


                if(isValidate()){

                    Registeration();

                }

                break;


        }

    }



    private boolean isValidate() {
        if (!ValidationUtility.edittextValidator(Txt_name,Txt_email,Txt_phone,Txt_password)) {
            return false;
        }

        if (!ValidationUtility.isValidPhone(Txt_phone.getText().toString())) {
            return false;
        }

        if(!ValidationUtility.isValidPassword(Txt_password)){

            return  false;

        }

        return true;
    }


    public void Registeration(){
        RegistrationRequest userObj = new RegistrationRequest();
        userObj.Email=Txt_email.getText().toString();
        userObj.Username=Txt_email.getText().toString();
        userObj.PhoneNumber=Txt_phone.getText().toString();
        userObj.FullName=Txt_name.getText().toString();
        userObj.Password=Txt_password.getText().toString();
        userObj.ConfirmPassword=Txt_password.getText().toString();

        showProgress();
        Log.d("test", "intest");

        Gson g = new Gson();
        String userJson= g.toJson(userObj);
        Log.d("test", userJson);


        RestClient.getAuthAdapter().registerUser(userObj).enqueue(new GeneralCallBack<GResponse>(this) {
            @Override
            public void onSuccess(GResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("test", Reslog);


                hideProgress();

                if(!response.getIserror()){

                    showMessageDailogNextScreen(getString(R.string.app_name),getString(R.string.msg_registration_successfull),Login.class);

                }
                else{

                    showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name),throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();

                hideProgress();
                Log.d("test", "failed");

            }






        });

    }





}
