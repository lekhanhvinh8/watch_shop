package com.example.myapplication.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.responses.OrderResponse;
import com.example.myapplication.ui.home.HomeAdapter;

import java.util.List;

public class OrderPendingConfirmAdapter extends RecyclerView.Adapter<OrderPendingConfirmAdapter.ViewHolder> {
    private List<OrderResponse> orders;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_order_pending_confirm, parent, false);
        return new OrderPendingConfirmAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderResponse order = orders.get(position);
    }

    @Override
    public int getItemCount() {
        if (orders == null) {
            return 0;
        }

        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.order_id);
        }
    }
}
