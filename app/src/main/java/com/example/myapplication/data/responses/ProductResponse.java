package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("_id")
    public String Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("description")
    public String Description;
    @SerializedName("origin")
    public String Origin;
    @SerializedName("sex")
    public String Sex;
    @SerializedName("sku")
    public String Sku;
    @SerializedName("brand")
    public String Brand;
    @SerializedName("glass")
    public String Glass;
    @SerializedName("machine")
    public String Machine;
    @SerializedName("strap")
    public String Strap;
    @SerializedName("dial_parameter")
    public double DialParameter;
    @SerializedName("dial_thickness")
    public double DialThickness;
    @SerializedName("dial_color")
    public String DialColor;
    @SerializedName("price")
    public double Price;
    @SerializedName("quantity")
    public int Quantity;
    @SerializedName("image")
    public String Image;

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getGlass() {
        return Glass;
    }

    public void setGlass(String glass) {
        Glass = glass;
    }

    public String getMachine() {
        return Machine;
    }

    public void setMachine(String machine) {
        Machine = machine;
    }

    public String getStrap() {
        return Strap;
    }

    public void setStrap(String strap) {
        Strap = strap;
    }

    public double getDialParameter() {
        return DialParameter;
    }

    public void setDialParameter(double dialParameter) {
        DialParameter = dialParameter;
    }

    public double getDialThickness() {
        return DialThickness;
    }

    public void setDialThickness(double dialThickness) {
        DialThickness = dialThickness;
    }

    public String getDialColor() {
        return DialColor;
    }

    public void setDialColor(String dialColor) {
        DialColor = dialColor;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
