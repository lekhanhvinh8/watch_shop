package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.ui.cart.CartFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle bundle = new Bundle();
        bundle.putString("accountId", getIntent().getStringExtra("accountId"));
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_user_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_user_cart:
                    selectedFragment = new CartFragment();
                    break;
                case R.id.nav_user_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            selectedFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, selectedFragment)
                    .commit();

            return true;
        });

        Fragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, homeFragment)
                .commit();
    }
}