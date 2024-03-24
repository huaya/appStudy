package com.example.appstduy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;

import com.example.appstduy.databinding.ActivityDashboardBinding;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DashboardActivity extends Activity implements View.OnClickListener  {

    private DashboardView dashboardView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardView = findViewById(R.id.scal_blu);
        button = findViewById(R.id.start_test);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startTest();
    }

    private void startTest() {
//        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            dashboardView.udDataSpeed(random.nextInt(280));
//        }
        dashboardView.udDataSpeed(50);
    }
}