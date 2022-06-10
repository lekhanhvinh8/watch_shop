package com.example.myapplication.ui.admin.order;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.OrderResponse;
import com.example.myapplication.databinding.AdminFragmentOrderBinding;
import com.example.myapplication.databinding.FragmentCartBinding;
import com.example.myapplication.ui.admin.brand.AdminBrandAdapter;
import com.example.myapplication.ui.admin.product.AdminProductAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderFragment extends Fragment {
    private static AdminOrderAdapter orderAdapter;
    private static List<OrderResponse> orders;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_order, container, false);

        RecyclerView rvOrder = view.findViewById(R.id.lOrder);
        orders = new ArrayList<>();
        orderAdapter = new AdminOrderAdapter(orders, getActivity());
        rvOrder.setAdapter(orderAdapter);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Call<List<OrderResponse>> call = RetrofitClient.getOrderService().GetAllAdminOrders();

        call.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                if (response.isSuccessful()) {
                    orders.clear();

                    List<OrderResponse> reverseOrders = new ArrayList<>();
                    for (int i = response.body().size() - 1; i >= 0; i--) {
                        reverseOrders.add(response.body().get(i));
                    }
                    orders.addAll(reverseOrders);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                Log.d("ORDER_ERROR", t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}