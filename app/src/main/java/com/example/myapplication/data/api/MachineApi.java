package com.example.myapplication.data.api;

import com.example.myapplication.data.requests.MachineRequest;
import com.example.myapplication.data.responses.MachineResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MachineApi {
    @GET("machines")
    Call<List<MachineResponse>> GetAll();

    @POST("machines")
    Call<MachineResponse> Create(@Body MachineRequest request);

    @PUT("machines/{machineId}")
    Call<MachineResponse> Update(@Path("machineId") String id, @Body MachineRequest request);

    @DELETE("machines/{machineId}")
    Call<ResponseMessage> Delete(@Path("machineId") String id);
}
