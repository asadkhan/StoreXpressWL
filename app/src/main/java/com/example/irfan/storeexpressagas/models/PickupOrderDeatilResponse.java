package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PickupOrderDeatilResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("iserror")
    @Expose
    private Boolean iserror;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("value")
    @Expose
    private Value value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIserror() {
        return iserror;
    }

    public void setIserror(Boolean iserror) {
        this.iserror = iserror;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    public class Value {

        @SerializedName("orderId")
        @Expose
        private Integer orderId;
        @SerializedName("orderstatusID")
        @Expose
        private Integer orderstatusID;
        @SerializedName("totalprice")
        @Expose
        private Integer totalprice;
        @SerializedName("ItemsLst")
        @Expose
        private List<ItemsLst> itemsLst = null;

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getOrderstatusID() {
            return orderstatusID;
        }

        public void setOrderstatusID(Integer orderstatusID) {
            this.orderstatusID = orderstatusID;
        }

        public Integer getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Integer totalprice) {
            this.totalprice = totalprice;
        }

        public List<ItemsLst> getItemsLst() {
            return itemsLst;
        }

        public void setItemsLst(List<ItemsLst> itemsLst) {
            this.itemsLst = itemsLst;
        }

    }
    public class ItemsLst {

        @SerializedName("itemId")
        @Expose
        private Integer itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;

        @SerializedName("imageURL")
        @Expose
        private String imageURL;



        @SerializedName("itemQty")
        @Expose
        private Integer itemQty;
        @SerializedName("itemPrice")
        @Expose
        private Integer itemPrice;

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getimageURL() {
            return imageURL;
        }

        public void setimageURL(String imageUrl) {
            this.imageURL = imageUrl;
        }

        public Integer getItemQty() {
            return itemQty;
        }

        public void setItemQty(Integer itemQty) {
            this.itemQty = itemQty;
        }

        public Integer getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(Integer itemPrice) {
            this.itemPrice = itemPrice;
        }

    }
}
