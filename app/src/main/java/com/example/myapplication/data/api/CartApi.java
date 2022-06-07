package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.CartRequest;
import com.example.myapplication.data.requests.CartUpdateRequest;
import com.example.myapplication.data.responses.CartResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartApi {
    @GET("carts/{accountId}")
    Call<List<CartResponse>> GetCartOfAccount(@Path("accountId") String accountId);

    @POST("carts")
    Call<ResponseMessage> Create(@Body CartRequest  request);

    @PUT("carts/{cartId}")
    Call<ResponseMessage> UpdateAmount(@Path("cartId") String cartId, @Body CartUpdateRequest request);
}
