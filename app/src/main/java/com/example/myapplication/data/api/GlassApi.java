package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.GlassRequest;
import com.example.myapplication.data.responses.GlassResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GlassApi {
    @GET("glasses")
    Call<List<GlassResponse>> GetAll();

    @POST("glasses")
    Call<GlassResponse> Create(@Body GlassRequest request);

    @PUT("glasses/{glassId}")
    Call<GlassResponse> Update(@Path("glassId") String id, @Body GlassRequest request);

    @DELETE("glasses/{glassId}")
    Call<ResponseMessage> Delete(@Path("glassId") String id);
}
