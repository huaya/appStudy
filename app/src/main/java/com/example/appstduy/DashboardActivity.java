package com.example.appstduy;

import android.app.Activity;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DashboardActivity extends Activity implements View.OnClickListener  {
    private static final String TAG = "DashboardActivity";

    private static final int START_TEST_MSG = 1;

    private DashboardView dashboardView;

    private Button button;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage:" + msg.what);
            if (msg.what == START_TEST_MSG) {
                startTest();
                this.sendEmptyMessageDelayed(START_TEST_MSG, 1000);
            }
        }
    };

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
        handler.sendEmptyMessageDelayed(START_TEST_MSG, 0);
    }

    private void startTest() {
        Random random = new Random();
        dashboardView.udDataSpeed(random.nextInt(180));
    }
}