package com.example.myapplication.data.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {
    @SerializedName("account_id")
    public String AccountId;
    @SerializedName("total")
    public double Total;
    @SerializedName("type")
    public String Type;
    @SerializedName("address")
    public String Address;
    @SerializedName("list_item")
    public List<OrderItemRequest> Items;

    public List<OrderItemRequest> getItems() {
        return Items;
    }

    public void setItems(List<OrderItemRequest> items) {
        Items = items;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
