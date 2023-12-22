package com.example.samplesensorproviderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccessSensorsActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_sensors);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TextView luxText = (TextView) findViewById(R.id.textViewLuminosity);
        TextView accText = (TextView) findViewById(R.id.textViewAccelerometer);

        LightSensorAccess lightSensorAccess = new LightSensorAccess(sensorManager, luxText);
        AccelerometerSensorAccess accSensorAccess = new AccelerometerSensorAccess(sensorManager, accText);
    }
}