package com.example.myapplication.ui.admin.order;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.BrandRequest;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.OrderResponse;
import com.example.myapplication.data.responses.ResponseMessage;
import com.example.myapplication.ui.admin.brand.AdminBrandAdapter;
import com.example.myapplication.ui.admin.brand.AdminBrandFragment;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ViewHolder> {

    public List<OrderResponse> orders;

    public AdminOrderAdapter(List<OrderResponse> orders, Context context) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public AdminOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View orderListView = inflater.inflate(R.layout.admin_item_order, parent, false);
        return new ViewHolder(orderListView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderResponse order = orders.get(position);

        boolean checked = true;
        if(String.valueOf(order.status).equals("PENDING_CONFIRM")){
            checked = false;
        }

        holder.orderIdTextView.setText(order.Id);

        DecimalFormat format = new DecimalFormat("#,###.#");
        String totalPrice = format.format(order.Total);
        holder.orderTotalTextView.setText(totalPrice + " VNĐ");
        holder.orderSwitch.setChecked(checked);
        holder.orderSwitch.setClickable(!checked);

        holder.orderSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){

                Call<ResponseMessage> call = RetrofitClient.getOrderService().Confirm(order.Id);

                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if (response.isSuccessful()) {
                            holder.orderSwitch.setChecked(true);
                            holder.orderSwitch.setClickable(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {

                    }
                });
            }
            else  {
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderIdTextView;
        public TextView orderTotalTextView;
        public Switch orderSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIdTextView = itemView.findViewById(R.id.admin_order_id);
            orderTotalTextView = itemView.findViewById(R.id.admin_order_total);
            orderSwitch = itemView.findViewById(R.id.admin_order_confirm);
        }
    }
}
