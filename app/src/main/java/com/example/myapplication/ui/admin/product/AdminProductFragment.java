package com.example.myapplication.ui.admin.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.responses.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class AdminProductFragment extends Fragment {
    private static List<ProductResponse> products;
    private static AdminProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_product, container, false);
        RecyclerView rvProduct = view.findViewById(R.id.product_admin_list);
        products = new ArrayList<>();
        productAdapter = new AdminProductAdapter(products);
        rvProduct.setAdapter(productAdapter);
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}