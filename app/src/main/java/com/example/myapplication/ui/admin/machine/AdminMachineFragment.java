package com.example.myapplication.ui.admin.machine;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.MachineRequest;
import com.example.myapplication.data.responses.MachineResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminMachineFragment extends Fragment {
    private static AdminMachineAdapter machineAdapter;
    private static List<MachineResponse> machines;

    public AdminMachineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMachines();

        Button btnCreate = view.findViewById(R.id.machine_btn_create);
        btnCreate.setOnClickListener(view1 -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View createView = inflater.inflate(R.layout.machine_create_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(createView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tạo", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.machine_create_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(getActivity(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    createMachine(editText.getText().toString());
                }
            });

            alertDialog.show();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_machine, container, false);
        RecyclerView rvMachine = view.findViewById(R.id.machine_list);
        machines = new ArrayList<>();
        machineAdapter = new AdminMachineAdapter(machines);
        rvMachine.setAdapter(machineAdapter);
        rvMachine.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void createMachine(String name) {
        MachineRequest request = new MachineRequest();
        request.Name = name;

        Call<MachineResponse> call = RetrofitClient.getMachineService().Create(request);
        call.enqueue(new Callback<MachineResponse>() {
            @Override
            public void onResponse(@NonNull Call<MachineResponse> call, @NonNull Response<MachineResponse> response) {
                if (response.isSuccessful()) {
                    machines.add(0, response.body());
                    machineAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MachineResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void getMachines() {
        Call<List<MachineResponse>> call = RetrofitClient.getMachineService().GetAll();

        call.enqueue(new Callback<List<MachineResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MachineResponse>> call, @NonNull Response<List<MachineResponse>> response) {
                if (response.isSuccessful()) {
                    machines.clear();
                    machines.addAll(response.body());
                    machineAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MachineResponse>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateMachine(MachineResponse machine, MachineRequest request, int position) {
        Call<MachineResponse> call = RetrofitClient.getMachineService().Update(machine.getId(), request);
        call.enqueue(new Callback<MachineResponse>() {
            @Override
            public void onResponse(@NonNull Call<MachineResponse> call, @NonNull Response<MachineResponse> response) {
                if (response.isSuccessful()) {
                    machines.set(position, response.body());
                    machineAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MachineResponse> call, @NonNull Throwable t) {
                Log.d("MACHINE_ERROR", t.getLocalizedMessage());
            }
        });
    }

    public static void deleteMachine(String id, int position) {
        Call<ResponseMessage> call = RetrofitClient.getMachineService().Delete(id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMessage> call, @NonNull Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    machines.remove(position);
                    machineAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMessage> call, @NonNull Throwable t) {
            }
        });
    }
}