package com.example.myapplication.data.requests;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("username")
    public String Username;
    @SerializedName("password")
    public String Password;

    public LoginRequest(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
