package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.ui.order.OrderViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {
    private OrderViewPagerAdapter adapter;
    private String[] titles = new String[]{"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TabLayout tabLayout = view.findViewById(R.id.order_tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.order_view_pager);
        adapter = new OrderViewPagerAdapter(getActivity());
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}