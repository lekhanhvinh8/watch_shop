package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    @SerializedName("_id")
    public String Id;

    @SerializedName("status")
    public String status;

    @SerializedName("address")
    public String Address;


    @SerializedName("total")
    public double Total;
    @SerializedName("items")
    public List<OrderItemResponse> Items;
}
