package com.example.myapplication.ui.admin.machine;

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
import com.example.myapplication.data.requests.MachineRequest;
import com.example.myapplication.data.responses.MachineResponse;

import java.util.List;

public class AdminMachineAdapter extends RecyclerView.Adapter<AdminMachineAdapter.ViewHolder> {
    private List<MachineResponse> machines;

    public AdminMachineAdapter(List<MachineResponse> machines) {
        this.machines = machines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View machineView = inflater.inflate(R.layout.machine_item, parent, false);
        return new AdminMachineAdapter.ViewHolder(machineView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MachineResponse machine = machines.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(machine.getName());

        holder.editButton.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View updateView = inflater.inflate(R.layout.machine_update_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setView(updateView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Lưu", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.machine_update_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(view.getContext(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    MachineRequest request = new MachineRequest();
                    request.Name = editText.getText().toString();
                    AdminMachineFragment.updateMachine(machine, request, position);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Huỷ", ((dialog, which) -> {

            }));

            alertDialog.show();
        });

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setMessage("Bạn có chắc muốn xoá loại máy này không ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Có", (dialog, which) ->
                    AdminMachineFragment.deleteMachine(machine.getId(), position));

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", ((dialog, which) -> {

            }));

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.machine_name);
            editButton = itemView.findViewById(R.id.machine_btn_update);
            deleteButton = itemView.findViewById(R.id.machine_btn_delete);
        }
    }
}
