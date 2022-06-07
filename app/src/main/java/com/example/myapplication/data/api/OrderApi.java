package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.OrderRequest;
import com.example.myapplication.data.responses.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderApi {
    @POST("orders")
    Call<ResponseMessage> Create(@Body OrderRequest request);
}
