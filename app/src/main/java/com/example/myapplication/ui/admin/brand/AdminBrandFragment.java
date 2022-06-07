package com.example.myapplication.ui.admin.brand;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.BrandRequest;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBrandFragment extends Fragment {
    private static AdminBrandAdapter brandAdapter;
    private static List<BrandResponse> brands;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_brand, container, false);
        RecyclerView rvBrands = view.findViewById(R.id.brand_list);
        brands = new ArrayList<>();
        brandAdapter = new AdminBrandAdapter(brands, getActivity());
        rvBrands.setAdapter(brandAdapter);
        rvBrands.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBrands();

        Button btnCreate = view.findViewById(R.id.brand_btn_create);
        btnCreate.setOnClickListener(view1 -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View createView = inflater.inflate(R.layout.brand_create_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(createView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tạo", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.brand_create_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(view.getContext(), "Không được bỏ trống", Toast.LENGTH_LONG);
                } else {
                    createBrand(editText.getText().toString());
                }
            });

            alertDialog.show();
        });
    }

    public void getBrands() {
        Call<List<BrandResponse>> call = RetrofitClient.getBrandService().GetAll();

        call.enqueue(new Callback<List<BrandResponse>>() {
            @Override
            public void onResponse(Call<List<BrandResponse>> call, Response<List<BrandResponse>> response) {
                if (response.isSuccessful()) {
                    brands.clear();
                    brands.addAll(response.body());
                    brandAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BrandResponse>> call, Throwable t) {
                Log.d("BRAND_ERROR", t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createBrand(String name) {
        BrandRequest request = new BrandRequest();
        request.Name = name;

        Call<BrandResponse> call = RetrofitClient.getBrandService().Create(request);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                if (response.isSuccessful()) {
                    brands.add(0, response.body());
                    brandAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onFailure(Call<BrandResponse> call, Throwable t) {

            }
        });
    }

    public static void updateBrand(BrandResponse brand, BrandRequest request, int position) {
        Call<BrandResponse> call = RetrofitClient.getBrandService().Update(brand.getId(), request);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                if (response.isSuccessful()) {
                    brands.set(position, response.body());
                    brandAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<BrandResponse> call, Throwable t) {

            }
        });
    }

    public static void deleteBrand(String id, int position) {
        Call<ResponseMessage> call = RetrofitClient.getBrandService().Delete(id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    brands.remove(position);
                    brandAdapter.notifyItemRemoved(position);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
            }
        });
    }
}