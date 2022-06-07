package com.example.myapplication.ui.admin.strap;

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
import com.example.myapplication.data.requests.StrapRequest;
import com.example.myapplication.data.responses.StrapResponse;

import java.util.List;

public class AdminStrapAdapter extends RecyclerView.Adapter<AdminStrapAdapter.ViewHolder> {
    private List<StrapResponse> straps;

    public AdminStrapAdapter(List<StrapResponse> straps) {
        this.straps = straps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View strapView = inflater.inflate(R.layout.strap_item, parent, false);
        return new AdminStrapAdapter.ViewHolder(strapView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StrapResponse strap = straps.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(strap.getName());

        holder.editButton.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View updateView = inflater.inflate(R.layout.strap_update_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setView(updateView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Lưu", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.strap_update_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(view.getContext(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    StrapRequest request = new StrapRequest();
                    request.Name = editText.getText().toString();
                    AdminStrapFragment.updateStrap(strap, request, position);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Huỷ", ((dialog, which) -> {

            }));

            alertDialog.show();
        });

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setMessage("Bạn có chắc muốn xoá loại dây đeo này không ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Có", (dialog, which) ->
                    AdminStrapFragment.deleteStrap(strap.getId(), position));

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", ((dialog, which) -> {

            }));

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return straps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.strap_name);
            editButton = itemView.findViewById(R.id.strap_btn_update);
            deleteButton = itemView.findViewById(R.id.strap_btn_delete);
        }
    }
}
