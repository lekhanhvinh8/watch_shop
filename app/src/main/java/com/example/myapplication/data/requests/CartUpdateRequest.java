package com.example.myapplication.data.requests;

import com.google.gson.annotations.SerializedName;

public class CartUpdateRequest {
    @SerializedName("amount")
    public int Amount;

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
