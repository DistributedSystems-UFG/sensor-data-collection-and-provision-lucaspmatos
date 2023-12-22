package com.example.samplesensorproviderapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import java.util.UUID;

public class AccelerometerSensorAccess implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mAcc;
    private TextView sensor_field;
    public static final String brokerURI = "54.147.89.192";

    Mqtt5BlockingClient client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost(brokerURI)
            .buildBlocking();

    public AccelerometerSensorAccess(SensorManager sm, TextView tv) {
        sensorManager = sm;
        sensor_field = tv;
        mAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
        client.connect();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The accelerometer sensor returns a single value.
        float acc = event.values[0];
        String value = String.format("%.2f", acc);

        // Publish the sensor value.
        publishSensor(value);

        // Show accelerometer value on the text field
        sensor_field.setText(value);
    }

    @Override
    protected void finalize() {
        sensorManager.unregisterListener(this);
    }

    public final void publishSensor(String value) {
        client.toAsync().publishWith()
                .topic("accelerometer")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(value.getBytes())
                .send();
    }
}