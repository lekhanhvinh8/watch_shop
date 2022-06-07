package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.LoginRequest;
import com.example.myapplication.data.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("accounts/login")
    Call<LoginResponse> Login(@Body LoginRequest request);
}
