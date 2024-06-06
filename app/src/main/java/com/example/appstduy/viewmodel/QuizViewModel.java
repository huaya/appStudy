package com.example.appstduy.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.appstduy.util.LogUtils;

public class QuizViewModel extends ViewModel {

    private static final String TAG = "QuizViewModel";

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.info(TAG, "ViewModel instance about to be destroyed");
    }
}
