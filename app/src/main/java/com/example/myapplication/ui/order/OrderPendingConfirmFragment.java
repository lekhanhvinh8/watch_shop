package com.example.myapplication.ui.order;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.OrderResponse;
import com.example.myapplication.ui.admin.brand.AdminBrandAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPendingConfirmFragment extends Fragment {
    public OrderPendingConfirmAdapter orderAdapter;
    public static List<OrderResponse> orders;
    public static String accountId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        accountId = args.getString("accountId", "");
        int tabIndex = getArguments().getInt("tabIndex");


        View view = inflater.inflate(R.layout.fragment_order_pending_confirm, container, false);
        RecyclerView rvOrderItem = view.findViewById(R.id.order_pending_confirm_list);

//        if(tabIndex == 1){
//            rvOrderItem = view.findViewById(R.id.order_pending_confirm_list1);
//        }

        orders = new ArrayList<>();
        orderAdapter = new OrderPendingConfirmAdapter(orders, accountId, getActivity());
        rvOrderItem.setAdapter(orderAdapter);
        rvOrderItem.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllOrders();
    }

    public void getAllOrders(){
        Call<List<OrderResponse>> call = RetrofitClient.getOrderService().GetAllOrders(accountId);

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


                    Log.d("positiontab_setChange", String.valueOf(orders.size()));
                    orderAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {

            }
        });
    }

}