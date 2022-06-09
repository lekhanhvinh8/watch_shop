package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.LoginRequest;
import com.example.myapplication.data.responses.LoginResponse;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtUsername = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(getBaseContext(), "Tài khoản hoặc mật khẩu đang bỏ trống", Toast.LENGTH_SHORT).show();
            } else {
                Login();
            }
        });
    }

    public void Login() {
        LoginRequest request = new LoginRequest(txtUsername.getText().toString(), txtPassword.getText().toString());

        Call<LoginResponse> responseCall = RetrofitClient.getAccountService().Login(request);
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    switch (Objects.requireNonNull(response.body()).getRole()) {
                        case "ADMIN": {
                            Intent intentAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                            intentAdmin.putExtra("accountId", response.body().getId());
                            new Handler().postDelayed((Runnable) () -> startActivity(intentAdmin), 500);
                            break;
                        }
                        case "USER": {
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            intent.putExtra("accountId", response.body().getId());
                            new Handler().postDelayed((Runnable) () -> startActivity(intent), 300);
                            break;
                        }
                    }
                } else {
                    if (response.code() == 400) {
                        try {
                            Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}