package com.example.irfan.storeexpressagas.models;

public class GeneralResponse {
    protected String developer_message;
    protected Integer status;
    public String getDeveloper_message() {
        return developer_message;
    }

    public void setDeveloper_message(String developer_message) {
        this.developer_message = developer_message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}

