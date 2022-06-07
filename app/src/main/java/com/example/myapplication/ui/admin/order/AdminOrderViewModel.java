package com.example.myapplication.ui.admin.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminOrderViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}