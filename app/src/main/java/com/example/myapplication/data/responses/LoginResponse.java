package com.example.myapplication.data.responses;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("_id")
    public String Id;
    @SerializedName("username")
    public String Username;
    @SerializedName("fullName")
    public String FullName;
    @SerializedName("phone")
    public String Phone;
    @SerializedName("role")
    public String Role;

    public LoginResponse(String username, String fullName, String phone, String role) {
        Username = username;
        FullName = fullName;
        Phone = phone;
        Role = role;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
