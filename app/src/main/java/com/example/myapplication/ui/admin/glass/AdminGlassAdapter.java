package com.example.myapplication.ui.admin.glass;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
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
import com.example.myapplication.data.requests.GlassRequest;
import com.example.myapplication.data.responses.GlassResponse;

import java.util.List;

public class AdminGlassAdapter extends RecyclerView.Adapter<AdminGlassAdapter.ViewHolder> {
    private List<GlassResponse> glasses;
    public AdminGlassAdapter(List<GlassResponse> glasses) {
        this.glasses = glasses;
    }

    @NonNull
    @Override
    public AdminGlassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View glassView = inflater.inflate(R.layout.glass_item, parent, false);
        return new AdminGlassAdapter.ViewHolder(glassView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminGlassAdapter.ViewHolder holder, int position) {
        GlassResponse glass = glasses.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(glass.getName());

        holder.editButton.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View updateView = inflater.inflate(R.layout.glass_update_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setView(updateView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Lưu", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.glass_update_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(view.getContext(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    GlassRequest request = new GlassRequest();
                    request.Name = editText.getText().toString();
                    AdminGlassFragment.updateGlass(glass, request, position);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Huỷ", ((dialog, which) -> {

            }));

            alertDialog.show();
        });

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setMessage("Bạn có chắc muốn xoá loại kính này không ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Có", (dialog, which) ->
                    AdminGlassFragment.deleteGlass(glass.getId(), position));

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", ((dialog, which) -> {

            }));

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return glasses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.glass_name);
            editButton = itemView.findViewById(R.id.glass_btn_update);
            deleteButton = itemView.findViewById(R.id.glass_btn_delete);
        }
    }
}
