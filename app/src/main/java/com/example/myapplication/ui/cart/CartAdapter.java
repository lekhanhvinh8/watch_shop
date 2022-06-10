package com.example.myapplication.ui.cart;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.requests.OrderItemRequest;
import com.example.myapplication.data.responses.CartResponse;
import com.example.myapplication.utilities.DownloadImage;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartResponse> carts;
    public SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public CartAdapter(List<CartResponse> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View cartView = inflater.inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(cartView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartResponse cart = carts.get(position);

        DecimalFormat format = new DecimalFormat("#,###.#");

        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(format.format(cart.getProductPrice()) + " VNĐ");

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(cart.getProductName());

        TextView amountTextView = holder.amountTextView;
        amountTextView.setText(Integer.toString(cart.getAmount()));

        TextView totalTextView = holder.totalTextView;
        Double total = cart.getProductPrice() * cart.getAmount();
        //totalTextView.setText(format.format(total) + " VNĐ");

        ImageView imageView = holder.imageView;
        new DownloadImage(imageView).execute(cart.getImage());

        ImageButton addButton = holder.addButton;
        addButton.setOnClickListener(view -> {
            CartFragment.plusAmount(cart.getId(), cart.getAmount() + 1, position);
        });

        ImageButton removeButton = holder.removeButton;
        removeButton.setOnClickListener(view -> {
            if (cart.getAmount() > 1) {
                CartFragment.removeAmount(cart.getId(), cart.getAmount() - 1, position);
            }
        });

        CheckBox checkBox = holder.checkBox;
        checkBox.setOnClickListener(view -> {
            if (!itemStateArray.get(position, false)) {
                checkBox.setChecked(true);
                itemStateArray.put(position, true);
            } else {
                checkBox.setChecked(false);
                itemStateArray.put(position, false);
            }
            CartFragment.setTotal();
        });

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < itemStateArray.size(); i++) {
            if (itemStateArray.get(i, false)) {
                total += carts.get(i).getProductPrice() * carts.get(i).getAmount();
            }
        }

        return total;
    }

    public List<OrderItemRequest> getSelectedList() {
        List<OrderItemRequest> result = new ArrayList<>();
        for (int i = 0; i < itemStateArray.size(); i++) {
            if (itemStateArray.get(i, false)) {
                CartResponse cart = carts.get(i);
                OrderItemRequest item = new OrderItemRequest();
                item.setName(cart.getProductName());
                item.setPrice(cart.getProductPrice());
                item.setImage(cart.getImage());
                item.setAmount(cart.getAmount());
                item.setProductId(cart.getProductId());
                item.setCartId(cart.getId());

                result.add(item);
            }
        }

        return result;
    }

    public void clearSelected() {
        for (int i = 0; i < itemStateArray.size(); i++) {
            if (itemStateArray.get(i, false)) {
                carts.remove(i);
                notifyItemChanged(i);
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView amountTextView;
        TextView totalTextView;
        ImageButton addButton;
        ImageButton removeButton;
        ImageView imageView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.cart_item_name);
            priceTextView = itemView.findViewById(R.id.cart_item_price);
            amountTextView = itemView.findViewById(R.id.cart_item_amount);
            //totalTextView = itemView.findViewById(R.id.cart_item_total);
            addButton = itemView.findViewById(R.id.cart_add_btn);
            removeButton = itemView.findViewById(R.id.cart_remove_btn);
            imageView = itemView.findViewById(R.id.cart_item_image);
            checkBox = itemView.findViewById(R.id.cart_item_checkbox);
        }

        void bind(int position) {
            if (!itemStateArray.get(position, false)) {
                checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
        }
    }
}
