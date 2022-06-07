package com.example.myapplication.ui.admin.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.AdminFragmentUserBinding;
import com.example.myapplication.databinding.FragmentCartBinding;

public class AdminUserFragment extends Fragment {

    private AdminFragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminUserViewModel cartViewModel =
                new ViewModelProvider(this).get(AdminUserViewModel.class);

        binding = AdminFragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}