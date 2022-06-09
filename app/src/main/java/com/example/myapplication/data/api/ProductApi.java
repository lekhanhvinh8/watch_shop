package com.example.myapplication.data.api;

import com.example.myapplication.data.responses.ProductCreateResposne;
import com.example.myapplication.data.responses.ProductResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProductApi {
    @GET("products")
    Call<List<ProductResponse>> GetAll();

    @GET("products/all")
    Call<List<ProductResponse>> GetAllProducts();

    @Multipart
    @POST("products")
    Call<ProductCreateResposne> Create(@Part MultipartBody.Part file,
                                       @Part MultipartBody.Part name,
                                       @Part MultipartBody.Part price,
                                       @Part MultipartBody.Part quantity);

    @DELETE("products/{productId}")
    Call<ResponseMessage> Delete(@Path("productId") String productId);
}
