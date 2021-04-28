package com.example.venux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.concurrent.TimeoutException;


public class PlayGame extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mLastAccelerometer = new float[3];
    float xVal, yVal, zVal;
    static final float ALPHA = 0.25f;
    private TextView X,Y,Z,toDo, isMoveOk;
    private Controller controller;
    private Button btn_start;
    private Button btn_reset;
    static Timer timer;
    private Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        timer = new Timer();
        X =  findViewById(R.id.playGame_TW_x);
        Y =  findViewById(R.id.playGame_TW_y);
        Z =  findViewById(R.id.playGame_TW_z);
        toDo = findViewById(R.id.playGame_TW_toDo);
        isMoveOk = findViewById(R.id.playGame_TW_isMoveOk);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        controller = new Controller();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        btn_start = findViewById(R.id.playGame_btn_start);
        btn_reset = findViewById(R.id.playGame_btn_resetGame);

        btn_start.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                changeText();
                startClick();
            }
        });

        btn_reset.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                controller.resetGame();
            }
        });

    }

    private void changeText(){
        if(controller.isNextRoundRecorderRound()){
            toDo.setText("Rec new move!");
        }else {
            toDo.setText("Kopy the move!");
        }
    }

    private void startClick(){

        //countDown();
        v.vibrate(50);
        if(controller.playNextRound(xVal,yVal,zVal)){
            isMoveOk.setText("Its ok!!!");
        }else {
            isMoveOk.setText("no, no, no.... Not ok!..");
            v.vibrate(1000);
        }

    };


    public void countDown(){
        int delay = 3000; // number of milliseconds to sleep

        long start = System.currentTimeMillis();
        while(start >= System.currentTimeMillis() - delay); // do nothing

    }










    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mLastAccelerometer = lowPass(sensorEvent.values.clone(),  mLastAccelerometer);

            xVal = sensorEvent.values[0];
            yVal = sensorEvent.values[1];
            zVal = sensorEvent.values[2];

            X.setText("X: " + Float.toString(xVal));
            Y.setText("Y: " + Float.toString(yVal));
            Z.setText("Z: " + Float.toString(zVal));
        }
    }
    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}