package com.example.sensorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sensing extends WearableActivity {

    private TextView mTextView;
    private Button mBtn_acl;
    private Button mBtn_gyro;
    private Button mBtn_step;
    private Button mBtn_heart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensing);

        mTextView = (TextView) findViewById(R.id.text);
        mBtn_acl = (Button) findViewById(R.id.accelerometerBtn);
        mBtn_gyro = (Button) findViewById(R.id.gyroscopeBtn);
        mBtn_step = (Button) findViewById(R.id.stepDetectorBtn);
        mBtn_heart = (Button) findViewById(R.id.hearRateBtn);

        mBtn_acl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sensing.this , Choose_A_Accelerometer.class));
            }
        });

        mBtn_gyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Sensing.this , Choose_A_Gyroscope.class));

            }
        });

        mBtn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sensing.this , HeartRate.class));
            }
        });

        mBtn_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sensing.this , StepCounter.class));
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
