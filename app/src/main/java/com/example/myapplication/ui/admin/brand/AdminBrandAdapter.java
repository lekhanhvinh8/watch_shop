package com.example.myapplication.ui.admin.brand;

import android.app.AlertDialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.requests.BrandRequest;
import com.example.myapplication.data.responses.BrandResponse;

import java.util.List;

public class AdminBrandAdapter extends RecyclerView.Adapter<AdminBrandAdapter.ViewHolder> {
    public List<BrandResponse> brands;
    private final Context context;

    public AdminBrandAdapter(List<BrandResponse> brands, Context context) {
        this.brands = brands;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View brandView = inflater.inflate(R.layout.brand_item, parent, false);
        Log.d("brand", brandView.toString());
        return new ViewHolder(brandView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrandResponse brand = brands.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(brand.getName());

        holder.editButton.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View updateView = inflater.inflate(R.layout.brand_update_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setView(updateView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Lưu", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.brand_update_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(view.getContext(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    BrandRequest request = new BrandRequest();
                    request.Name = editText.getText().toString();

                    AdminBrandFragment.updateBrand(brand, request, position);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Huỷ", ((dialog, which) -> {

            }));

            alertDialog.show();
        });

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setMessage("Bạn có chắc muốn xoá thương hiệu này không ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Có", (dialog, which) ->
                    AdminBrandFragment.deleteBrand(brand.getId(), position));

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", ((dialog, which) -> {

            }));

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.brand_name);
            editButton = itemView.findViewById(R.id.brand_btn_update);
            deleteButton = itemView.findViewById(R.id.brand_btn_delete);
        }
    }
}
