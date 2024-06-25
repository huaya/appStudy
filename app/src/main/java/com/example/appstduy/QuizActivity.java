package com.example.appstduy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.logging.Logger;

public class QuizActivity extends FragmentActivity {

    private static final String TAG = "QuizActivity";

    private static final String KEY_INDEX = "index";

    private static final String CHEAT_MARK = "cheat_mark";

    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;

    private Button mFalseButton;

    private Button mNextButton;

    private Button mPrveButton;

    private Button mCheatButton;

    private TextView mQuestionTextView;

    private final Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa, false),
            new Question(R.string.question_turkey, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_americas, true)
    };

    private int mCurrentIndex = 0;

    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(CHEAT_MARK, false);
        }
        updateQuestion();
        mQuestionTextView.setOnClickListener(view -> {
            if (mCurrentIndex < mQuestionBank.length) {
                mCurrentIndex++;
            }
            updateQuestion();
        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(view -> {
            Logger.getLogger("QuizActivity").info("ture button click!");
            checkAnswer(true);
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(view -> {
            Log.i(TAG, "false button click!");
            checkAnswer(false);
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(view -> {
            Intent intent = CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(view -> {
            if (mCurrentIndex < mQuestionBank.length - 1) {
                mCurrentIndex++;
            }
            updateQuestion();
        });

        mPrveButton = findViewById(R.id.prev_button);
        mPrveButton.setOnClickListener(view -> {
            if (mCurrentIndex > 0) {
                mCurrentIndex--;
            }
            updateQuestion();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState(Bundle) called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(CHEAT_MARK, mIsCheater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            } else{
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }
}