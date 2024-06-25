package com.example.appstduy;

import androidx.lifecycle.ViewModel;

import com.example.appstduy.util.LogUtils;

public class QuizViewModel extends ViewModel {

    private static final String TAG = "QuizViewModel";

    private int mCurrentIndex = 0;

    final Question[] mQuestionBank = new Question[]{
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
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    }
}
