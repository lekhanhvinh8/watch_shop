package com.example.myapplication.ui.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.CartUpdateRequest;
import com.example.myapplication.data.requests.OrderItemRequest;
import com.example.myapplication.data.requests.OrderRequest;
import com.example.myapplication.data.responses.CartResponse;
import com.example.myapplication.data.responses.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private static List<CartResponse> carts;
    private static CartAdapter cartAdapter;
    private static String accountId;
    private static Context context;
    private Button orderButton;
    private static TextView totalTextView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCarts();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView rvCart = view.findViewById(R.id.cart_list);
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(carts);
        rvCart.setAdapter(cartAdapter);
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        accountId = getArguments().getString("accountId");
        context = getActivity();
        orderButton = view.findViewById(R.id.cart_order_btn);
        orderButton.setOnClickListener(view1 -> {
            order();
        });

        totalTextView = view.findViewById(R.id.cart_total);

        return view;
    }

    private void order() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.cart_order_dialog, null);
        alertDialog.setView(view);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Xác nhận", ((dialog, which) -> {
            EditText addressEditText = alertDialog.findViewById(R.id.cart_order_address);
            if (TextUtils.isEmpty(addressEditText.getText().toString())) {
                Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                return;
            } else {
                OrderRequest request = new OrderRequest();
                request.setAccountId(accountId);
                request.setAddress(addressEditText.getText().toString());
                request.setTotal(cartAdapter.getTotal());
                request.setType("COD");
                request.setItems(cartAdapter.getSelectedList());
                Call<ResponseMessage> call = RetrofitClient.getOrderService().Create(request);
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if (response.isSuccessful()) {
                            cartAdapter.clearSelected();
                            totalTextView.setText("0 VNĐ");
                            Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new GsonBuilder().create();
                            try {
                                ResponseMessage error = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }));

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Huỷ", ((dialog, which) -> {
        }));

        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getCarts() {
        Call<List<CartResponse>> call = RetrofitClient.getCartService().GetCartOfAccount(accountId);

        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if (response.isSuccessful()) {
                    carts.clear();
                    carts.addAll(response.body());
                    cartAdapter.notifyDataSetChanged();

                    setTotal();
                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });
    }

    public static void plusAmount(String cartId, int amount, int position) {
        CartUpdateRequest request = new CartUpdateRequest();
        request.setAmount(amount);
        Call<ResponseMessage> call = RetrofitClient.getCartService().UpdateAmount(cartId, request);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    CartResponse cart = carts.get(position);
                    cart.setAmount(amount);
                    carts.set(position, cart);
                    cartAdapter.notifyItemChanged(position);
                    setTotal();
                } else {
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage error = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public static void removeAmount(String cartId, int amount, int position) {
        CartUpdateRequest request = new CartUpdateRequest();
        request.setAmount(amount);
        Call<ResponseMessage> call = RetrofitClient.getCartService().UpdateAmount(cartId, request);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    CartResponse cart = carts.get(position);
                    cart.setAmount(amount);
                    carts.set(position, cart);
                    cartAdapter.notifyItemChanged(position);
                    setTotal();
                } else {
                    Gson gson = new GsonBuilder().create();
                    try {
                        ResponseMessage error = gson.fromJson(response.errorBody().string(), ResponseMessage.class);
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
    }

    public static void setTotal() {
        DecimalFormat format = new DecimalFormat("#,###.#");
        totalTextView.setText(format.format(cartAdapter.getTotal()) + " VNĐ");
    }
}