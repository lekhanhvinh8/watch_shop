package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.OrderRequest;
import com.example.myapplication.data.responses.CartResponse;
import com.example.myapplication.data.responses.OrderResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApi {
    @POST("orders")
    Call<ResponseMessage> Create(@Body OrderRequest request);

    @GET("orders/{accountId}")
    Call<List<OrderResponse>> GetAllOrders(@Path("accountId") String accountId);

    @GET("orders/")
    Call<List<OrderResponse>> GetAllAdminOrders();

    @PUT("orders/confirm/{orderId}")
    Call<ResponseMessage> Confirm(@Path("orderId") String orderId);
}
