package com.example.sensorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Choose_A_Sensor extends WearableActivity {

    private TextView mTextView;
    private Button mGyro;
    private Button mAccel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__a__sensor);

        mTextView = (TextView) findViewById(R.id.text);
        mAccel = (Button) findViewById(R.id.accBtn);
        mGyro = (Button) findViewById(R.id.gyroBtn);

        mAccel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_A_Sensor.this , Choose_A_Accelerometer.class));
            }

        });

        mGyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_A_Sensor.this , Choose_A_Gyroscope.class));
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
