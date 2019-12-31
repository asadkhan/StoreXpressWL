package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductReqRequest {

    @SerializedName("requestedProduct")
    @Expose
    private String requestedProduct;
    @SerializedName("productDesc")
    @Expose
    private String productDesc;
    @SerializedName("image")
    @Expose
    private String image;

    public String getRequestedProduct() {
        return requestedProduct;
    }

    public void setRequestedProduct(String requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
