package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

public class OrderItemResponse {
    @SerializedName("_id")
    public String Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("image")
    public String Image;
    @SerializedName("price")
    public double Price;
    @SerializedName("total")
    public double Total;
    @SerializedName("amount")
    public int Amount;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}
