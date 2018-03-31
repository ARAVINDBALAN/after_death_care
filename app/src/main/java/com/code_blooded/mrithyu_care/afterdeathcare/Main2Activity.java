package com.code_blooded.mrithyu_care.afterdeathcare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ssaurel on 02/12/2016.
 */
public class Main2Activity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Showing splash screen with a timer.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your application main_activity
                Intent i = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(i);

                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT); // Timer
    }
    }
