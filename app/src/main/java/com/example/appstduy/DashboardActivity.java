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

    private static final int MAX_TEST_TIME = 50;

    private DashboardView dashboardView;

    private CanvaTestView canvaTestView;

    private CircleMeterView circleMeterView;


    private Button button;

    private int testTime = 0;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage:" + msg.what);
            if (msg.what == START_TEST_MSG) {
                startTest();
                if (testTime < MAX_TEST_TIME) {
                    this.sendEmptyMessageDelayed(START_TEST_MSG, 1000);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardView = findViewById(R.id.scal_blu);
        canvaTestView = findViewById(R.id.canva_test);
        circleMeterView = findViewById(R.id.circle_meter);
        button = findViewById(R.id.start_test);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        testTime = 0;
        startTest();
//        handler.sendEmptyMessageDelayed(START_TEST_MSG, 0);
    }

    private void startTest() {
        testTime++;
        Random random = new Random();
        int speed= random.nextInt(180);
        dashboardView.udDataSpeed(speed);
        canvaTestView.udDataSpeed(speed);
        circleMeterView.setProgress(random.nextInt(100));
    }
}