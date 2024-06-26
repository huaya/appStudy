package com.example.appstduy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Optional;
import java.util.logging.Logger;

public class QuizActivity extends FragmentActivity {

    private static final String TAG = "QuizActivity";

    private static final String KEY_INDEX = "index";

    private static final String CHEAT_MARK = "cheat_mark";

    private Button mTrueButton;

    private Button mFalseButton;

    private Button mNextButton;

    private Button mPrveButton;

    private Button mCheatButton;

    private TextView mQuestionTextView;

    private final Lazy<QuizViewModel> quizViewModelLazy = new Lazy<>(() -> new ViewModelProvider(this).get(QuizViewModel.class));

    private boolean mIsCheater;

    private final ActivityResultLauncher<Intent> mCheatLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    mIsCheater = CheatActivity.wasAnswerShown(result.getData());
                }
            });

    private QuizViewModel getViewModel() {
        return quizViewModelLazy.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
         if (savedInstanceState != null) {
             getViewModel().setCurrentIndex(savedInstanceState.getInt(KEY_INDEX, 0));
             mIsCheater = savedInstanceState.getBoolean(CHEAT_MARK, false);
         }
        setContentView(R.layout.activity_quiz);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(view -> {
            moveToNext();
        });
        updateQuestion();

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
            mCheatLauncher.launch(CheatActivity.newIntent(QuizActivity.this, getViewModel().currentQuestionAnswer()));
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(view -> {
            moveToNext();
        });

        mPrveButton = findViewById(R.id.prev_button);
        mPrveButton.setOnClickListener(view -> {
            getViewModel().moveToPrve();
            Optional.ofNullable(mNextButton).ifPresent(bt -> bt.setClickable(getViewModel().canMoveToNext()));
            Optional.ofNullable(mPrveButton).ifPresent(bt -> bt.setClickable(getViewModel().canMoveToPrve()));
            updateQuestion();
        });
        mNextButton.setClickable(getViewModel().canMoveToNext());
        mPrveButton.setClickable(getViewModel().canMoveToPrve());
    }

    private void moveToNext() {
        getViewModel().moveToNext();
        Optional.ofNullable(mNextButton).ifPresent(bt -> bt.setClickable(getViewModel().canMoveToNext()));
        Optional.ofNullable(mPrveButton).ifPresent(bt -> bt.setClickable(getViewModel().canMoveToPrve()));
        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState(Bundle) called");

        // 只要在未结束使用的activity进入停止状态时（比如用户按了Home按钮，启动另一个应用时），操作系统都
        // 会调用Activity.onSaveInstanceState(Bundle)。这个时间点很重要，因为停止的activity会被标记
        // 为killable。如果应用进程因低优先级被“杀死”，那么，你大可放心
        // Activity.onSaveInstanceState(Bundle)肯定已被调用过。
        // onSaveInstanceState(Bundle)的默认实现要求所有activity视图将自身状态数据保存在Bundle对象
        // 中。Bundle是存储字符串键与特定类型值之间映射关系（键值对）的一种结构。
        // 通过bundle保存页面数据
         savedInstanceState.putInt(KEY_INDEX,  getViewModel().getCurrentIndex());
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
        int question = getViewModel().currentQuestionText();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = getViewModel().currentQuestionAnswer();
        int messageResId;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }
}