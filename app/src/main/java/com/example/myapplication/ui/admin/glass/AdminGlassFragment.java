package com.example.myapplication.ui.admin.glass;

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
import com.example.myapplication.data.requests.GlassRequest;
import com.example.myapplication.data.responses.GlassResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminGlassFragment extends Fragment {
    private static AdminGlassAdapter glassAdapter;
    private static List<GlassResponse> glasses;
    public AdminGlassFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_glass, container, false);
        RecyclerView rvGlass = view.findViewById(R.id.glass_list);
        glasses = new ArrayList<>();
        glassAdapter = new AdminGlassAdapter(glasses);
        rvGlass.setAdapter(glassAdapter);
        rvGlass.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getGlasses();

        Button btnCreate = view.findViewById(R.id.glass_btn_create);
        btnCreate.setOnClickListener(view1 -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View createView = inflater.inflate(R.layout.glass_create_dialog, null);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(createView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tạo", (dialog, which) -> {
                EditText editText = alertDialog.findViewById(R.id.glass_create_edit_text);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(getActivity(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    createGlass(editText.getText().toString());
                }
            });

            alertDialog.show();
        });
    }

    private void createGlass(String name) {
        GlassRequest request = new GlassRequest();
        request.Name = name;

        Call<GlassResponse> call = RetrofitClient.getGlassService().Create(request);
        call.enqueue(new Callback<GlassResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlassResponse> call, @NonNull Response<GlassResponse> response) {
                if (response.isSuccessful()) {
                    glasses.add(0, response.body());
                    glassAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GlassResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void getGlasses() {
        Call<List<GlassResponse>> call = RetrofitClient.getGlassService().GetAll();

        call.enqueue(new Callback<List<GlassResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GlassResponse>> call, @NonNull Response<List<GlassResponse>> response) {
                if (response.isSuccessful()) {
                    glasses.clear();
                    glasses.addAll(response.body());
                    glassAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GlassResponse>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateGlass(GlassResponse glass, GlassRequest request, int position) {
        Call<GlassResponse> call = RetrofitClient.getGlassService().Update(glass.getId(), request);
        call.enqueue(new Callback<GlassResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlassResponse> call, @NonNull Response<GlassResponse> response) {
                if (response.isSuccessful()) {
                    glasses.set(position, response.body());
                    glassAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GlassResponse> call, @NonNull Throwable t) {
                Log.d("GLASS_ERROR", t.getLocalizedMessage());
            }
        });
    }

    public static void deleteGlass(String id, int position) {
        Call<ResponseMessage> call = RetrofitClient.getGlassService().Delete(id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMessage> call, @NonNull Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    glasses.remove(position);
                    glassAdapter.notifyItemRemoved(position);
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMessage> call, @NonNull Throwable t) {
            }
        });
    }
}