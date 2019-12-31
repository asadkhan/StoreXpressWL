package com.example.irfan.storeexpressagas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FproductResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("iserror")
    @Expose
    private Boolean iserror;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("date")
    @Expose
    private Object date;
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }


    public class Value {

        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("totalQuantity")
        @Expose
        private int totalQuantity;


        @SerializedName("quantity")
        @Expose
        private Object quantity;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("itemsize")
        @Expose
        private Object itemsize;
        @SerializedName("sIunit")
        @Expose
        private Object sIunit;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("manufacturer")
        @Expose
        private Object manufacturer;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("addedOn")
        @Expose
        private String addedOn;
        @SerializedName("addedBy")
        @Expose
        private String addedBy;

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(int Qty) {
            this.totalQuantity =Qty ;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getQuantity() {
            return quantity;
        }

        public void setQuantity(Object quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getItemsize() {
            return itemsize;
        }

        public void setItemsize(Object itemsize) {
            this.itemsize = itemsize;
        }

        public Object getSIunit() {
            return sIunit;
        }

        public void setSIunit(Object sIunit) {
            this.sIunit = sIunit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(Object manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

    }

}
