package com.example.myapplication.ui.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.CartResponse;
import com.example.myapplication.data.responses.OrderResponse;
import com.example.myapplication.ui.home.HomeAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPendingConfirmAdapter extends RecyclerView.Adapter<OrderPendingConfirmAdapter.ViewHolder> {
    private List<OrderResponse> orders;
    private final Context context;
    private String accountId;

    public OrderPendingConfirmAdapter(List<OrderResponse> orders, String accountId, Context context) {
        this.orders = orders;
        this.accountId = accountId;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_order_pending_confirm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderResponse order = orders.get(position);

        String orderStatus = "";
        if(String.valueOf(order.status).equals("PENDING_CONFIRM")){
            orderStatus = "Chưa xác nhận";
        }
        else {
            orderStatus = "Đã giao";
        }

        holder.orderIdTextView.setText(order.Id);
        holder.orderAddressTextView.setText(order.Address);

        DecimalFormat format = new DecimalFormat("#,###.#");
        String totalPrice = format.format(order.Total);

        holder.orderPriceTextView.setText(totalPrice + " VNĐ");
        holder.orderStatusTextView.setText(orderStatus);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView orderAddressTextView;
        TextView orderStatusTextView;
        TextView orderPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIdTextView = itemView.findViewById(R.id.order_id);
            orderAddressTextView = itemView.findViewById(R.id.order_address);
            orderStatusTextView = itemView.findViewById(R.id.order_status);
            orderPriceTextView = itemView.findViewById(R.id.order_price);;
        }
    }
}
