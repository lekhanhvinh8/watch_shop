package com.example.myapplication.data.requests;

import com.google.gson.annotations.SerializedName;

public class CartRequest {
    @SerializedName("account_id")
    public String AccountId;
    @SerializedName("product_id")
    public String ProductId;
    @SerializedName("amount")
    public int Amount;

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
