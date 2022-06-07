package com.example.myapplication.data;

import com.example.myapplication.data.api.AccountApi;
import com.example.myapplication.data.api.BrandApi;
import com.example.myapplication.data.api.CartApi;
import com.example.myapplication.data.api.GlassApi;
import com.example.myapplication.data.api.MachineApi;
import com.example.myapplication.data.api.OrderApi;
import com.example.myapplication.data.api.ProductApi;
import com.example.myapplication.data.api.StrapApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "http://172.18.29.40:3000/api/";

    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static AccountApi getAccountService() {
        AccountApi accountApi = getRetrofit().create(AccountApi.class);

        return accountApi;
    }

    public static BrandApi getBrandService() {
        BrandApi brandApi = getRetrofit().create(BrandApi.class);

        return brandApi;
    }

    public static GlassApi getGlassService() {
        GlassApi glassApi = getRetrofit().create(GlassApi.class);

        return glassApi;
    }

    public static MachineApi getMachineService() {
        MachineApi machineApi = getRetrofit().create(MachineApi.class);

        return machineApi;
    }

    public static StrapApi getStrapService() {
        StrapApi strapApi = getRetrofit().create(StrapApi.class);

        return strapApi;
    }

    public static ProductApi getProductService() {
        ProductApi productApi = getRetrofit().create(ProductApi.class);

        return productApi;
    }

    public static CartApi getCartService() {
        CartApi cartApi = getRetrofit().create(CartApi.class);

        return cartApi;
    }

    public static OrderApi getOrderService() {
        OrderApi orderApi = getRetrofit().create(OrderApi.class);

        return orderApi;
    }
}
