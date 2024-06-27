package com.example.appstduy;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.appstduy.util.LogUtils;

public class QuizViewModel extends ViewModel {

    private static final String TAG = "QuizViewModel";

    private int mCurrentIndex = 0;

    private final Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa, false),
            new Question(R.string.question_turkey, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_americas, true)
    };

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.info(TAG, "ViewModel instance about to be destroyed");
    }

    public boolean currentQuestionAnswer() {
        return mQuestionBank[mCurrentIndex].isAnswerTrue();
    }

    public int currentQuestionText() {
        return mQuestionBank[mCurrentIndex].getTextResId();
    }

    public void moveToNext() {
        if (canMoveToNext()) {
            mCurrentIndex++;
        }
    }

    public void moveToPrve() {
        if (canMoveToPrve()) {
            mCurrentIndex--;
        }
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public Question[] getQuestionBank() {
        return mQuestionBank;
    }

    public boolean canMoveToNext() {
        LogUtils.debug(TAG, "canMoveToNext:%d", mCurrentIndex);
        return mCurrentIndex < mQuestionBank.length - 1;
    }

    public boolean canMoveToPrve() {
        LogUtils.info(TAG, "canMoveToPrve:%d", mCurrentIndex);
        return mCurrentIndex > 0;
    }

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
    }
}
