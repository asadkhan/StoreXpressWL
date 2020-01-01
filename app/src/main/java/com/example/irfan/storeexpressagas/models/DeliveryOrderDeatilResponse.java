package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryOrderDeatilResponse {

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

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
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



    public class Value {

        @SerializedName("orderId")
        @Expose
        private Integer orderId;
        @SerializedName("orderstatusID")
        @Expose
        private Integer orderstatusID;
        @SerializedName("orderStatus")
        @Expose
        private String orderStatus;
        @SerializedName("orderType")
        @Expose
        private Integer orderType;
        @SerializedName("totalprice")
        @Expose
        private Integer totalprice;
        @SerializedName("assignToID")
        @Expose
        private Object assignToID;
        @SerializedName("assignToName")
        @Expose
        private Object assignToName;
        @SerializedName("orderDate")
        @Expose
        private String orderDate;
        @SerializedName("orderTime")
        @Expose
        private String orderTime;
        @SerializedName("itemsLst")
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

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public Integer getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Integer totalprice) {
            this.totalprice = totalprice;
        }

        public Object getAssignToID() {
            return assignToID;
        }

        public void setAssignToID(Object assignToID) {
            this.assignToID = assignToID;
        }

        public Object getAssignToName() {
            return assignToName;
        }

        public void setAssignToName(Object assignToName) {
            this.assignToName = assignToName;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public List<ItemsLst> getItemsLst() {
            return itemsLst;
        }

        public void setItemsLst(List<ItemsLst> itemsLst) {
            this.itemsLst = itemsLst;
        }

    }
}
