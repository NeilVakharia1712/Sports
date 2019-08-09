package com.example.sensorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Choose_A_Gyroscope extends WearableActivity {

    private ToggleButton xBtn;
    private ToggleButton yBtn;
    private ToggleButton zBtn;
    private Button magBtn;
    int x_isOn = 0;
    int y_isOn = 0;
    int z_isOn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__a__gyroscope);

        xBtn = (ToggleButton) findViewById(R.id.xBtn);
        yBtn = (ToggleButton) findViewById(R.id.yBtn);
        zBtn = (ToggleButton) findViewById(R.id.zBtn);
        magBtn = (Button) findViewById(R.id.magBtn);

/*        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_A_Accelerometer.this , AccelerometerX.class));
            }

        });

        yBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_A_Accelerometer.this , AccelerometerY.class));
            }

        });

        zBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_A_Accelerometer.this , AccelerometerZ.class));
            }

        });*/

        xBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    x_isOn = 1;
                } else {
                    // The toggle is disabled
                    //Intent intent=new Intent(Accelerometer.this,Accelerometer.class);
                    //startActivity(intent);
                    //finish();

                    x_isOn = 0;
                }
            }
        });

        yBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    y_isOn = 1;
                } else {
                    // The toggle is disabled
                    //Intent intent=new Intent(Accelerometer.this,Accelerometer.class);
                    //startActivity(intent);
                    //finish();

                    y_isOn = 0;
                }
            }
        });

        zBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    z_isOn = 1;
                } else {
                    // The toggle is disabled
                    //Intent intent=new Intent(Accelerometer.this,Accelerometer.class);
                    //startActivity(intent);
                    //finish();

                    z_isOn = 0;
                }
            }
        });




        magBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(x_isOn == 1 && y_isOn ==0 && z_isOn == 0){
                    startActivity(new Intent(Choose_A_Gyroscope.this , GyroscopeX.class));
                }

                else if(x_isOn == 0 && y_isOn ==1 && z_isOn == 0){
                    startActivity(new Intent(Choose_A_Gyroscope.this , GyroscopeY.class));
                }

                else if(x_isOn == 0 && y_isOn ==0 && z_isOn == 1){
                    startActivity(new Intent(Choose_A_Gyroscope.this , GyroscopeZ.class));
                }
                else if(x_isOn == 1 && y_isOn ==1 && z_isOn == 1){
                    startActivity(new Intent(Choose_A_Gyroscope.this , Gyroscope.class));
                }
                else if(x_isOn == 1 && y_isOn ==1 && z_isOn == 0){
                    startActivity(new Intent(Choose_A_Gyroscope.this , GyroscopeXY.class));
                }else if(x_isOn == 1 && y_isOn ==0 && z_isOn == 1){
                    startActivity(new Intent(Choose_A_Gyroscope.this , GyroscopeXZ.class));
                }else if(x_isOn == 0 && y_isOn ==1 && z_isOn == 1){
                    startActivity(new Intent(Choose_A_Gyroscope.this , GyroscopeYZ.class));
                }else
                {
                    //doNothing
                }

            }

        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
