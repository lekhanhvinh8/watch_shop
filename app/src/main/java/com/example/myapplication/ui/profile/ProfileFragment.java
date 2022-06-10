package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.ui.order.OrderPendingConfirmFragment;
import com.example.myapplication.ui.order.OrderViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {
    private OrderViewPagerAdapter adapter;
    private String[] titles = new String[]{"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String accountId = getArguments().getString("accountId");

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TabLayout tabLayout = view.findViewById(R.id.order_tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.order_view_pager);

        //viewPager.setOffscreenPageLimit(1);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new OrderViewPagerAdapter(fm, getLifecycle(), accountId);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("positiontab_onPageSele", String.valueOf(position));
                //OrderPendingConfirmFragment.getAllOrders();
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            //tab.setText(titles[position]);
            tab.setText(titles[position]);
        })).attach();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}