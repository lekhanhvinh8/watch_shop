package com.example.myapplication.data.requests;

import com.google.gson.annotations.SerializedName;

public class OrderItemRequest {
    @SerializedName("name")
    public String Name;
    @SerializedName("image")
    public String Image;
    @SerializedName("price")
    public double Price;
    @SerializedName("amount")
    public int Amount;
    @SerializedName("product_id")
    public String ProductId;
    @SerializedName("cart_id")
    public String CartId;

    public String getCartId() {
        return CartId;
    }

    public void setCartId(String cartId) {
        CartId = cartId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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
}
