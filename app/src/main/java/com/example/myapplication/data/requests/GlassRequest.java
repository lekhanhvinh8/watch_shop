package com.example.myapplication.data.requests;

import com.google.gson.annotations.SerializedName;

public class GlassRequest {
    @SerializedName("name")
    public String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
