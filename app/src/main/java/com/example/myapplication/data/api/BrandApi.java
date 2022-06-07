package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.BrandRequest;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BrandApi {
    @GET("brands")
    Call<List<BrandResponse>> GetAll();

    @POST("brands")
    Call<BrandResponse> Create(@Body BrandRequest request);

    @PUT("brands/{brandId}")
    Call<BrandResponse> Update(@Path("brandId") String id, @Body BrandRequest request);

    @DELETE("brands/{brandId}")
    Call<ResponseMessage> Delete(@Path("brandId") String id);
}
