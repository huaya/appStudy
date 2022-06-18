package com.example.appstduy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;

    private Button mFalseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(view -> {
            Logger.getLogger("QuizActivity").info("ture button click!");
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(view -> {
            Logger.getLogger("QuizActivity").info("false button click!");
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        });
    }
}