package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.SerializedName;

public class ItemVM {

    @SerializedName("Id")
    public int Id;
    @SerializedName("Quantity")
    public int Quantity;
    @SerializedName("Name")
    public String Name;
    @SerializedName("Itemsize")
    public String Itemsize;
    @SerializedName("SIunit")
    public String SIunit;
    @SerializedName("Price")
    public String Price;
    @SerializedName("Image")
    public String Image;
    @SerializedName("Description")
    public String Description;
    @SerializedName("Manufacturer")
    public String Manufacturer;
    @SerializedName("CategoryId")
    public String CategoryId;
    @SerializedName("Category")
    public String Category;
    @SerializedName("IsActive")
    public Boolean IsActive;
    @SerializedName("AddedOn")
    public String AddedOn;
    @SerializedName("AddedBy")
    public String AddedBy;
}
