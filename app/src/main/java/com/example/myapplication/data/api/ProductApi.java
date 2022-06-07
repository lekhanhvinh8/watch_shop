package com.example.myapplication.data.api;

import com.example.myapplication.data.responses.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {
    @GET("products")
    Call<List<ProductResponse>> GetAll();
}
