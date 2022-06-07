package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.StrapRequest;
import com.example.myapplication.data.responses.ResponseMessage;
import com.example.myapplication.data.responses.StrapResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StrapApi {
    @GET("straps")
    Call<List<StrapResponse>> GetAll();

    @POST("straps")
    Call<StrapResponse> Create(@Body StrapRequest request);

    @PUT("straps/{strapId}")
    Call<StrapResponse> Update(@Path("strapId") String id, @Body StrapRequest request);

    @DELETE("straps/{strapId}")
    Call<ResponseMessage> Delete(@Path("strapId") String id);
}
