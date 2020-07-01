package com.shoppingapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shoppingapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
