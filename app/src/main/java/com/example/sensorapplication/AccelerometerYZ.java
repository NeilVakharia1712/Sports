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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class AccelerometerYZ extends WearableActivity {

    private TextView mTextView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    String TAG = "WearActivity";
    private List<Sensor> sensors;
    static float x;
    static float y;
    static float z;
    private static DecimalFormat df = new DecimalFormat("0.00");
    float max = 0.0f;
    private TextView xMax;
    private Button resetBtn;

    private String macAddress;
    private ToggleButton mToggle;
    private JavaGetRequest mTask;
    int on_off = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_xy);

        mTextView = (TextView) findViewById(R.id.text);
        xMax = (TextView) findViewById(R.id.xMax);
        resetBtn = (Button) findViewById(R.id.resetBtn);


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
                Intent intent=new Intent(AccelerometerYZ.this,AccelerometerYZ.class);
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

        sensors = sensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
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
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];


                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    String msg = "Accelerometer" + "\n x: " + String.valueOf(event.values[0]) +
                            "\n y: " + String.valueOf(event.values[1]) +
                            "\n z: " + String.valueOf(event.values[2]); //+
                    //"\n 3: " + String.valueOf(event.values[3]) +    //for the TYPE_ROTATION_VECTOR these 2 exist.
                    //"\n 4: " + String.valueOf(event.values[4]);


                    float k = (float) Math.sqrt(((event.values[1] * event.values[1]) + (event.values[2] * event.values[2])));
                    String nums = df.format(k);
                    mTextView.setText(nums);

                    String url_value_string = "0/"+Float.toString(y)+"/"+Float.toString(z);
                    final String[] output = new String[2];
                    long unixTime = System.currentTimeMillis() / 1000L;
                    String time = Long.toString(unixTime);
                    output[0] = "http://165.124.181.163:5000/store/"+time+"/"+macAddress+"/"+"Accelerometer/"+url_value_string;

                    if(on_off ==1) {
                        new JavaGetRequest().execute(output);
                    }





                    if(k > max){
                        max = k;
                        String my_max = df.format(k);
                        xMax.setText(my_max);

                        /*Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        long[] vibrationPattern = {0, 500, 500, 500, 500, 500};
                        int[] mAmplitudes = new int[]{0, 255, 255, 255, 255, 255};
                        VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, mAmplitudes, -1);
                        vibrator.vibrate(effect);
*/
                    }

                    // nums = nums.substring(0,4);

                    if (k > 11 && k < 20) {
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        // long[] vibrationPattern = {0, 100, 50, 100};
                        // int[] mAmplitudes = new int[]{0, 10, 0, 10};
                        // VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, mAmplitudes, -1);
                        //  vibrator.vibrate(effect);
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_HEAVY_CLICK));
                    }

                    if (k > 30) {
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        long[] vibrationPattern = {0, 500, 500, 500, 500, 500};
                        int[] mAmplitudes = new int[]{0, 255, 255, 255, 255, 255};
                        VibrationEffect effect = VibrationEffect.createWaveform(vibrationPattern, mAmplitudes, -1);
                        vibrator.vibrate(effect);




                    }



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
