package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequest {
    @SerializedName("items")
    public List<ItemVM> items;

}
