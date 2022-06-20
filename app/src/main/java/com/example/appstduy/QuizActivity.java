package com.example.appstduy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private static final String KEY_INDEX = "index";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();
        mQuestionTextView.setOnClickListener(view -> {
            if(mCurrentIndex < mQuestionBank.length) {
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
            Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
            startActivity(intent);
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(view -> {
            if(mCurrentIndex < mQuestionBank.length - 1) {
                mCurrentIndex++;
            }
            updateQuestion();
        });

        mPrveButton = findViewById(R.id.prev_button);
        mPrveButton.setOnClickListener(view -> {
            if(mCurrentIndex > 0) {
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

    private void checkAnswer(boolean userPressTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        Toast.makeText(QuizActivity.this, answerIsTrue == userPressTrue ? R.string.correct_toast : R.string.incorrect_toast,
                Toast.LENGTH_SHORT).show();
    }
}