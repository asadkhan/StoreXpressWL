package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileDetailsResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("iserror")
    @Expose
    private Boolean iserror;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("value")
    @Expose
    private Value value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }


    public class Value {

        @SerializedName("addedOn")
        @Expose
        private String addedOn;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("userAddress")
        @Expose
        private List<UserAddress> userAddress = null;

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public List<UserAddress> getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(List<UserAddress> userAddress) {
            this.userAddress = userAddress;
        }

    }




    public class UserAddress {

        @SerializedName("id")
        @Expose
        private Double id;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("addressType")
        @Expose
        private String addressType;
        @SerializedName("addedOn")
        @Expose
        private String addedOn;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;

        public Double getId() {
            return id;
        }

        public void setId(Double id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

    }
}
