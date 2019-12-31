package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.SerializedName;

public class OrderRequest {

    @SerializedName("PaymentStatus")
    public static int PaymentStatus=-1 ;
    @SerializedName("OrderType")
    public static int OrderType=-1 ;
    @SerializedName("PaymentMeathod")
    public static int PaymentMeathod =-1;
    @SerializedName("Address")
    public static String Address ;
    @SerializedName("ContactNo")
    public static String ContactNo ;
}
