package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage {
    @SerializedName("message")
    public String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
