package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

public class CartResponse {
    @SerializedName("_id")
    public String Id;
    @SerializedName("account_id")
    public int AccountId;
    @SerializedName("product_name")
    public String ProductName;
    @SerializedName("product_price")
    public double ProductPrice;
    @SerializedName("image")
    public String Image;
    @SerializedName("amount")
    public int Amount;
    @SerializedName("product_id")
    public String ProductId;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
