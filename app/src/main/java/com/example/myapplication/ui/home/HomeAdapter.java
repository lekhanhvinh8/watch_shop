package com.example.myapplication.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.responses.ProductResponse;
import com.example.myapplication.utilities.DownloadImage;

import java.text.DecimalFormat;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<ProductResponse> products;

    public HomeAdapter(List<ProductResponse> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View productView = inflater.inflate(R.layout.item_product, parent, false);
        return new HomeAdapter.ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        ProductResponse product = products.get(position);
        DecimalFormat format = new DecimalFormat("#,###.#");

        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(format.format(product.getPrice()) + " VNĐ");

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(product.getName());

        ImageView imageView = holder.imageView;
        new DownloadImage(imageView).execute(product.getImage());

        Button addToCartBtn = holder.addToCartBtn;
        addToCartBtn.setOnClickListener(view -> {
            HomeFragment.addToCart(product.getId());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView priceTextView;
        TextView nameTextView;
        Button addToCartBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.user_image_product);
            priceTextView = itemView.findViewById(R.id.user_price_product);
            nameTextView = itemView.findViewById(R.id.user_name_product);
            addToCartBtn = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
