package com.example.sensorapplication;


import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class StepCounter extends WearableActivity {

    private TextView mTextView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    String TAG = "WearActivity";
    private List<Sensor> sensors;
    private static DecimalFormat df = new DecimalFormat("0");
    private Button resetBtn;

    private String macAddress;
    private ToggleButton mToggle;
    private JavaGetRequest mTask;
    int on_off = 0;
    int start = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        mTextView = (TextView) findViewById(R.id.text);
        resetBtn = (Button) findViewById(R.id.reset);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        if (sensors.size() < 1) {
            Toast.makeText(this, "No sensors returned from getSensorList", Toast.LENGTH_SHORT).show();
            Log.wtf(TAG, "No sensors returned from getSensorList");
        }
        Sensor[] sensorArray = sensors.toArray(new Sensor[sensors.size()]);
        for (int i = 0; i < sensorArray.length; i++) {
            Log.wtf(TAG, "Found sensor " + i + " " + sensorArray[i].toString());
        }

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StepCounter.this,StepCounter.class);
                startActivity(intent);
                finish();
            }

        });

        macAddress =
                android.provider.Settings.Secure.getString(this.getApplicationContext().getContentResolver(), "android_id");

        mToggle = (ToggleButton) findViewById(R.id.toggleButton);
        //ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    on_off = 1;
                } else {
                    // The toggle is disabled
                    //Intent intent=new Intent(Accelerometer.this,Accelerometer.class);
                    //startActivity(intent);
                    //finish();

                    on_off = 0;
                }
            }
        });



        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensor();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensor();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //just to make sure.
        unregisterSensor();

    }


    void registerSensor() {
        //just in case
        if (sensorManager == null)
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensors = sensorManager.getSensorList(Sensor.TYPE_STEP_DETECTOR);
        if (sensors.size() > 0)
            sensor = sensors.get(0);

        listener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                //just set the values to a textview so they can be displayed.

                if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

                    ++start;

                    mTextView.setText(Integer.toString(start));

                    String url_value_string = Integer.toString(start)+"/"+"0/0";
                    final String[] output = new String[2];
                    long unixTime = System.currentTimeMillis() / 1000L;
                    String time = Long.toString(unixTime);
                    output[0] = "http://165.124.181.163:5000/store/"+time+"/"+macAddress+"/"+"StepDetector/"+url_value_string;

                    if(on_off ==1) {
                        new JavaGetRequest().execute(output);
                    }



                    // nums = nums.substring(0,4);



                }
            }
        };
        sensorManager.registerListener(listener, sensor, 1);



    }

    void unregisterSensor() {
        if (sensorManager != null && listener != null) {
            sensorManager.unregisterListener(listener);
        }
        // clean up and release memory.
        sensorManager = null;
        listener = null;


    }
}
