package com.example.myapplication.ui.admin.product;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.ProductResponse;
import com.example.myapplication.ui.admin.brand.AdminBrandAdapter;
import com.example.myapplication.ui.admin.brand.AdminBrandFragment;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {
    public List<ProductResponse> products;

    public AdminProductAdapter(List<ProductResponse> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View productView = inflater.inflate(R.layout.admin_product_item, parent, false);

        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminProductAdapter.ViewHolder holder, int position) {
        ProductResponse product = products.get(position);

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(product.getName());

        String price = Double.toString(product.getPrice());
        if(price != null){
            DecimalFormat format = new DecimalFormat("#,###.#");
            String totalPrice = format.format(product.getPrice());

            TextView priceTextView = holder.productPrice;
            priceTextView.setText(totalPrice + " VNĐ");
        }

        String quantity = Integer.toString(product.getQuantity());
        if(quantity != null){
            TextView quantityTextView = holder.productQuantity;
            quantityTextView.setText(quantity);
        }

        ImageView imageView = holder.imageView;
        new DownloadImageTask(imageView)
                .execute(product.getImage());

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setMessage("Bạn có chắc muốn xoá sản phẩm này không ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Có", (dialog, which) ->
                    AdminProductFragment.deleteProduct(product.getId(), position));

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", ((dialog, which) -> {

            }));

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView productPrice;
        public TextView productQuantity;
        public ImageView imageView;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.admin_product_name);
            productPrice = itemView.findViewById(R.id.admin_product_price);
            productQuantity = itemView.findViewById(R.id.admin_product_quantity);
            imageView = (ImageView) itemView.findViewById(R.id.admin_product_image);
            deleteButton = itemView.findViewById(R.id.admin_product_removeBtn);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();

                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error download image", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


