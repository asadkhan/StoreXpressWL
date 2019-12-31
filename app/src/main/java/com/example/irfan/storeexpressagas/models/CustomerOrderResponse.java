package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerOrderResponse {
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
    private List<Value> value = null;

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

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
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
        @SerializedName("orderDate")
        @Expose
        private String orderDate;
        @SerializedName("itemsLst")
        @Expose
        private Object itemsLst;

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

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public Object getItemsLst() {
            return itemsLst;
        }

        public void setItemsLst(Object itemsLst) {
            this.itemsLst = itemsLst;
        }

    }

}
