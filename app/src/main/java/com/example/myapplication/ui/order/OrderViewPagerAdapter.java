package com.example.myapplication.ui.order;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderViewPagerAdapter extends FragmentStateAdapter {
    private String[] titles = new String[]{"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao"};
    private String accountId;
    private OrderPendingConfirmFragment pendingFragment;

//    public OrderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String accountId) {
//        super(fragmentActivity);
//        this.accountId = accountId;
//    }

    public OrderViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String accountId) {
        super(fragmentManager, lifecycle);
        this.accountId = accountId;

        pendingFragment = newInstance(accountId);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OrderPendingConfirmFragment pendingFragment = new OrderPendingConfirmFragment();
        Bundle args = new Bundle();
        args.putString("accountId", accountId);
        args.putInt("tabIndex", position);
        pendingFragment.setArguments(args);


//        switch (position) {
//            case 0:{
//                return pendingFragment;
//            }
//            case 1:{
//                return pendingFragment;
//            }
//            case 2:{
//                return pendingFragment;
//            }
//            case 3:{
//                return pendingFragment;
//            }
//        }

        return pendingFragment;
        //return this.pendingFragment;
    }

    public static OrderPendingConfirmFragment newInstance(String accountId) {
        OrderPendingConfirmFragment pendingFragment = new OrderPendingConfirmFragment();
        Bundle args = new Bundle();
        args.putString("accountId", accountId);
        pendingFragment.setArguments(args);

        return pendingFragment;
    }


    @Override
    public int getItemCount() {
        return titles.length;
        //return 4;
    }
}
