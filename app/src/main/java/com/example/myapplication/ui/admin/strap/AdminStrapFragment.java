package com.example.myapplication.ui.admin.strap;

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
import com.example.myapplication.data.responses.ResponseMessage;
import com.example.myapplication.data.requests.StrapRequest;
import com.example.myapplication.data.responses.StrapResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminStrapFragment extends Fragment {
    private static AdminStrapAdapter strapAdapter;
    private static List<StrapResponse> straps;
    public AdminStrapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getStraps();

        Button btnCreate = view.findViewById(R.id.strap_btn_create);
        btnCreate.setOnClickListener(view1 -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View createView = inflater.inflate(R.layout.strap_create_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(createView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tạo", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.strap_create_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(getActivity(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    createStrap(editText.getText().toString());
                }
            });

            alertDialog.show();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_strap, container, false);
        RecyclerView rvStrap = view.findViewById(R.id.strap_list);
        straps = new ArrayList<>();
        strapAdapter = new AdminStrapAdapter(straps);
        rvStrap.setAdapter(strapAdapter);
        rvStrap.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void createStrap(String name) {
        StrapRequest request = new StrapRequest();
        request.Name = name;

        Call<StrapResponse> call = RetrofitClient.getStrapService().Create(request);
        call.enqueue(new Callback<StrapResponse>() {
            @Override
            public void onResponse(@NonNull Call<StrapResponse> call, @NonNull Response<StrapResponse> response) {
                if (response.isSuccessful()) {
                    straps.add(0, response.body());
                    strapAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StrapResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void getStraps() {
        Call<List<StrapResponse>> call = RetrofitClient.getStrapService().GetAll();

        call.enqueue(new Callback<List<StrapResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<StrapResponse>> call, @NonNull Response<List<StrapResponse>> response) {
                if (response.isSuccessful()) {
                    straps.clear();
                    straps.addAll(response.body());
                    strapAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StrapResponse>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateStrap(StrapResponse strap, StrapRequest request, int position) {
        Call<StrapResponse> call = RetrofitClient.getStrapService().Update(strap.getId(), request);
        call.enqueue(new Callback<StrapResponse>() {
            @Override
            public void onResponse(@NonNull Call<StrapResponse> call, @NonNull Response<StrapResponse> response) {
                if (response.isSuccessful()) {
                    straps.set(position, response.body());
                    strapAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StrapResponse> call, @NonNull Throwable t) {
                Log.d("STRAP_ERROR", t.getLocalizedMessage());
            }
        });
    }

    public static void deleteStrap(String id, int position) {
        Call<ResponseMessage> call = RetrofitClient.getStrapService().Delete(id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMessage> call, @NonNull Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    straps.remove(position);
                    strapAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMessage> call, @NonNull Throwable t) {
            }
        });
    }
}