package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("Email")
    public String Email;

    @SerializedName("Username")
public String Username;

    @SerializedName("FullName")
    public String FullName;

    @SerializedName("PhoneNumber")
public String PhoneNumber;

    @SerializedName("Password")
public String Password;

    @SerializedName("ConfirmPassword")
public String ConfirmPassword;


}
