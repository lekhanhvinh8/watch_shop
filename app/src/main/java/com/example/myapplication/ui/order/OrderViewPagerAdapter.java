package com.example.myapplication.ui.order;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderViewPagerAdapter extends FragmentStateAdapter {
    private String[] titles = new String[]{"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao"};

    public OrderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OrderPendingConfirmFragment();
            case 1:
                return new OrderPendingDeliveryFragment();
        }

        return new OrderPendingConfirmFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
