package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

public class ProductCreateResposne {
    @SerializedName("message")
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
