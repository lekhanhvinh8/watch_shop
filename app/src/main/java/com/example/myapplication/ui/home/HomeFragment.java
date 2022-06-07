package com.example.myapplication.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.CartRequest;
import com.example.myapplication.data.responses.CartResponse;
import com.example.myapplication.data.responses.ProductResponse;
import com.example.myapplication.data.responses.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private static List<ProductResponse> products;
    private static HomeAdapter homeAdapter;
    private static Context context;
    private static String accountId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProducts();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rvHome = view.findViewById(R.id.product_user_list);
        products = new ArrayList<>();
        homeAdapter = new HomeAdapter(products);
        homeAdapter.notifyDataSetChanged();

        int numberOfColumns = 2;
        rvHome.setAdapter(homeAdapter);
        rvHome.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        context = getActivity();
        accountId = getArguments().getString("accountId");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getProducts() {
        Call<List<ProductResponse>> call = RetrofitClient.getProductService().GetAll();

        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    products.clear();
                    products.addAll(response.body());
                    homeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
            }
        });
    }

    public static void addToCart(String productId) {
        CartRequest request = new CartRequest();
        request.setProductId(productId);
        request.setAccountId(accountId);
        request.setAmount(1);
        Call<ResponseMessage> call = RetrofitClient.getCartService().Create(request);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage error = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(context, error.getMessage() , Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.d("CART", t.getLocalizedMessage());
            }
        });
    }
}