package com.example.myapplication.ui.admin.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.AdminFragmentOrderBinding;
import com.example.myapplication.databinding.FragmentCartBinding;

public class AdminOrderFragment extends Fragment {

    private AdminFragmentOrderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminOrderViewModel cartViewModel =
                new ViewModelProvider(this).get(AdminOrderViewModel.class);

        binding = AdminFragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}